package com.meeting.booking.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.meeting.booking.controller.BookingController;
import com.meeting.booking.dao.BookingDao;
import com.meeting.booking.dao.MeetingRoomDao;
import com.meeting.booking.dao.UserDao;
import com.meeting.booking.enums.Status;
import com.meeting.booking.exceptions.ApplicationExceptions;
import com.meeting.booking.exceptions.ApplicationResponseCode;
import com.meeting.booking.model.Booking;
import com.meeting.booking.model.MeetingRoom;
import com.meeting.booking.model.NewBooking;
import com.meeting.booking.model.User;
import com.meeting.booking.pojo.ActiveBookingResponse;
import com.meeting.booking.pojo.AvailibilityResponse;
import com.meeting.booking.pojo.BookingDetails;
import com.meeting.booking.pojo.CheckAvailibilty;
import com.meeting.booking.utils.RestResponse;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDao bookingDao;
	@Autowired
	private MeetingRoomDao meetingRoomDao;
	@Autowired
	private BookingController bookingcontroller;
	@Autowired
	private UserDao userDao;
	@Autowired
	private MailService mailService;

	@Override
	public boolean book(BookingDetails bookingDetails) throws ApplicationExceptions {
		if (bookingDetails == null || StringUtils.isEmpty(bookingDetails.getUserId()))
			throw new ApplicationExceptions(ApplicationResponseCode.USER_ID_NULL);
		int id = bookingDetails.getRoomId();
		String sTime = bookingDetails.getStartTime();
		String eTime = bookingDetails.getEndTime();
		CheckAvailibilty c1 = new CheckAvailibilty(id, sTime, eTime);
		boolean b2 = bookingDao.checkStatus(c1);
		if (b2 == false) {
			throw new ApplicationExceptions(ApplicationResponseCode.ALREADY_BOOKED);

		}

		/*
		 * boolean b1= bookingcontroller.checkAvailability(c1).isError(); if(b1==true) {
		 * throw new ApplicationExceptions(ApplicationResponseCode.ALREADY_BOOKED); }
		 */

		User user = userDao.findById(bookingDetails.getUserId());
		if (user == null)
			throw new ApplicationExceptions(ApplicationResponseCode.NOT_VALID_USER);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date startTime = null;
		Date endTime = null;
		Integer formateDate1;
		Integer formateDate2;
		Integer startMinute;
		Integer endMinute;
		String[] splitStartTime;
		String[] splitEndTime;
		try {
			String[] splitStartDate = bookingDetails.getStartTime().split(" ");
			String[] splitEndDate = bookingDetails.getEndTime().split(" ");

			String[] SplitStartDate1 = splitStartDate[0].split("/");
			String[] SplitEndDate2 = splitEndDate[0].split("/");
			splitStartTime = splitStartDate[1].split(":");
			splitEndTime = splitEndDate[1].split(":");

			startMinute = (Integer.parseInt(splitStartTime[0]) * 60) + Integer.parseInt(splitStartTime[1]);
			endMinute = (Integer.parseInt(splitEndTime[0]) * 60) + Integer.parseInt(splitEndTime[1]);

			
			if (endMinute.equals(startMinute)) {
				throw new ApplicationExceptions(ApplicationResponseCode.SAME_TIME);
			}
			if ((endMinute - startMinute) > 600)
				throw new ApplicationExceptions(ApplicationResponseCode.LONG_BOOKING);
			if ((endMinute < startMinute))
				throw new ApplicationExceptions(ApplicationResponseCode.MULTIPLE_DAY);

			formateDate1 = Integer.parseInt(SplitStartDate1[2] + "" + SplitStartDate1[1] + "" + SplitStartDate1[0]);
			formateDate2 = Integer.parseInt(SplitEndDate2[2] + "" + SplitEndDate2[1] + "" + SplitEndDate2[0]);

			startTime = dateFormat.parse(bookingDetails.getStartTime());
			endTime = dateFormat.parse(bookingDetails.getEndTime());
		} catch (ParseException e) {
			return false;
		}

		if (startTime.after(endTime))
			throw new ApplicationExceptions(ApplicationResponseCode.WROND_DATE_SELECTED);
		MeetingRoom meetingRoom = meetingRoomDao.findById(bookingDetails.getRoomId());
		if (meetingRoom == null)
			throw new ApplicationExceptions(ApplicationResponseCode.INVALID_ROOM);
//		Booking booking = new Booking(bookingDetails.getPurpose(), startTime, endTime, meetingRoom.getId(),
//				user.getId(), Status.BOOKED);
//		NewBooking booking2=new NewBooking(formateDate1, startMinute, formateDate2, endMinute, booking.getBookedRoom(), booking.getStatus());
//		bookingDao.save(booking,booking2);
		Booking booking = new Booking();
		booking.setPurpose(bookingDetails.getPurpose());
		booking.setStartTime(startTime);
		booking.setEndTime(endTime);
		booking.setBookedRoom(meetingRoom.getId());
		booking.setBookedBy(user.getId());
		booking.setStatus(Status.BOOKED);

		NewBooking newBooking = new NewBooking();
		newBooking.setStartDate(formateDate1);
		newBooking.setStartTime(startMinute);
		newBooking.setEndDate(formateDate2);
		newBooking.setEndTime(endMinute);
		newBooking.setBookedRoom(meetingRoom.getId());
		newBooking.setStatus(Status.BOOKED);

		booking.setNewBooking(newBooking);
		newBooking.setBooking(booking);
		bookingDao.save(booking, null);

		try {
			String st = formateDate1 + "T" + splitStartTime[0] + "" + splitStartTime[1] + "00";
			String et = formateDate2 + "T" + splitEndTime[0] + "" + splitEndTime[1] + "00";
			String st1 = formateDate1 + "T" + splitEndTime[0] + "" + splitEndTime[1] + "00";
			String email = user.getEmailId();
			String purpose = booking.getPurpose();
			String roomName = meetingRoom.getRoomName();
			BookingServiceImpl.send(st, et, st1, email, purpose, roomName);
//mailService.sendBookingConfirmationMail(user, meetingRoom, booking);
		} catch (Exception e) {
// TODO: handle exception
		}
		return true;
	}

	@Override
	public boolean isAvailableBetween(int roomId, Date startTime, Date endTime) {
		List<Booking> bookings = bookingDao.getAll();
		if (CollectionUtils.isEmpty(bookings))
			return true;
		for (Booking booking : bookings) {
			if (booking.getBookedRoom() == roomId && isBlocked(booking, startTime, endTime))
				return false;
		}
		return true;
	}

	@Override
	public List<Booking> bookingBetweenDate(Date startTime, Date endTime) {
		List<Booking> bookings = bookingDao.getAll();
		if (CollectionUtils.isEmpty(bookings))
			return null;
		return bookings.stream().filter(b -> isBlocked(b, startTime, endTime)).collect(Collectors.toList());
	}

	private boolean isBlocked(Booking b, Date startTime, Date endTime) {
		if (startTime.equals(b.getStartTime()) || endTime.equals(b.getEndTime())
				|| (b.getStartTime().after(startTime) && b.getStartTime().before(endTime))
				|| (b.getEndTime().after(startTime)))
			return true;
		return false;
	}

	@Override
	public List<ActiveBookingResponse> getFromCurrentDate(int id) {
		// TODO Auto-generated method stub
		return bookingDao.getFromCurrentDate(id);
	}

	@Override
	public List<AvailibilityResponse> myBookings(int id) {
		// TODO Auto-generated method stub
		return bookingDao.myBookings(id);
	}

	@Override
	public boolean deleteById(int id) {
// TODO Auto-generated method stub
		return bookingDao.deleteById(id);
	}

// create an event in mail
	public static void send(String st, String et, String st1, String email, String purpose, String roomName)
			throws Exception {

		try {
			String from = "bookmycr@atmecs.com";
			String to = email;
			Properties prop = new Properties();

			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.host", "smtp.gmail.com");
			prop.put("mail.smtp.port", "587");

			final String username = "bookmycr@atmecs.com";
			final String password = "Atmecs@123456789";

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			// Define message
			MimeMessage message = new MimeMessage(session);
			message.addHeaderLine("method=REQUEST");
			message.addHeaderLine("charset=UTF-8");
			message.addHeaderLine("component=VEVENT");

			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(" Your Conference Room " + roomName + " has Been Booked");

			StringBuffer sb = new StringBuffer();

			StringBuffer buffer = sb
					.append("BEGIN:VCALENDAR\n" + "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n"
							+ "VERSION:2.0\n" + "METHOD:REQUEST\n" + "BEGIN:VTIMEZONE\n"
							// + "TZID:America/New_York\n"
							+ "TZID=Asia/Kolkata\n"
							// + "X-LIC-LOCATION:America/New_York\n"
							+ "X-LIC-LOCATION:Asia/Kolkata\n" + "BEGIN:STANDARD\n"
							// + "DTSTART:20071104T020000\n"
							// + "TZOFFSETFROM:-0400\n"
							// + "TZOFFSETTO:-0500\n"
							+ "TZNAME:EST\n" + "END:STANDARD\n" + "BEGIN:DAYLIGHT\n"
							// + "DTSTART:20070311T020000\n"
							// + "TZOFFSETFROM:-0500\n"
							// + "TZOFFSETTO:-0400\n"
							+ "TZNAME:EDT\n" + "END:DAYLIGHT\n" + "END:VTIMEZONE\n" + "BEGIN:VEVENT\n"
							+ "TRANSP:OPAQUE\n" + "CREATED:" + st + "\n" + "LAST-MODIFIED:" + et + "\n"
							+ "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:" + email + "\n"
							+ "ORGANIZER:MAILTO:hockeyonicethricewastoldcold?.com\n" + "DTSTART;TZID=Asia/Kolkata:" + st
							+ "\n" + "DTEND;TZID=Asia/Kolkata:" + st1 + "\n"
							+ "RRULE:FREQ=DAILY;BYDAY=MO,TU,WE,TH,FR,SA,SU;UNTIL=" + et + "\n"
							// +"RRULE:FREQ=DAILY;BYDAY=MO,TU,WE,TH,FR,SA,SU\n"
							// +"RRULE:FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR;INTERVAL=1;COUNT=1\n"
							// +"RRULE:FREQ=DAILY;INTERVAL=2;BYDAY=MO,TU,WE,TH,FR;BYHOUR=10,11\n"
							+ "LOCATION:" + roomName + "\n" + "TRANSP:OPAQUE\n" + "SEQUENCE:0\n"
							// +
							// "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n"
							// + "UID:${starDateString}\n"
							+ "DTSTAMP:" + st + "\n" + "CATEGORIES:Meeting\n" + "DESCRIPTION:" + purpose + "\n"
							+ "SUMMARY:Your Conference Room has Been Booked\n" + "PRIORITY:5\n" + "CLASS:PUBLIC\n"
							+ "BEGIN:VALARM\n" + "TRIGGER:PT1440M\n" + "ACTION:DISPLAY\n" + "DESCRIPTION:Reminder\n"
							+ "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
			messageBodyPart.setHeader("Content-ID", "calendar_message");
			messageBodyPart
					.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(), "text/calendar")));// very
																													// important

			// Create a Multipart
			Multipart multipart = new MimeMultipart();

			// Add part one
			multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			message.setContent(multipart);

			// send message

			Transport.send(message);

			System.out.println("Success");

		} catch (MessagingException me) {
			me.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
