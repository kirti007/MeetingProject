package com.meeting.booking.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	int id=bookingDetails.getRoomId();
		String sTime= bookingDetails.getStartTime();
	     String eTime=	bookingDetails.getEndTime(); 
	     CheckAvailibilty c1=new CheckAvailibilty(id, sTime, eTime);
	     boolean b2=bookingDao.checkStatus(c1);
	   if(b2==false)
	    		 {
	    	  throw new ApplicationExceptions(ApplicationResponseCode.ALREADY_BOOKED); 

	    		 }
		
	  /*  boolean b1= bookingcontroller.checkAvailability(c1).isError();
	      if(b1==true) {
	    	  throw new ApplicationExceptions(ApplicationResponseCode.ALREADY_BOOKED); 
	      } */
		
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
		try {
			String []splitStartDate =bookingDetails.getStartTime().split(" ");
			String []splitEndDate =bookingDetails.getEndTime().split(" ");
			
			String []SplitStartDate1=splitStartDate[0].split("/");
			String []SplitEndDate2=splitEndDate[0].split("/");
			String[] splitStartTime=splitStartDate[1].split(":");
			String[] splitEndTime=splitEndDate[1].split(":");

		 startMinute=(Integer.parseInt(splitStartTime[0])*60)+Integer.parseInt(splitStartTime[1]);
		 endMinute=(Integer.parseInt(splitEndTime[0])*60)+Integer.parseInt(splitEndTime[1]);

			formateDate1=Integer.parseInt(SplitStartDate1[2]+""+SplitStartDate1[1]+""+SplitStartDate1[0]);
			formateDate2=Integer.parseInt(SplitEndDate2[2]+""+SplitEndDate2[1]+""+SplitEndDate2[0]);
			

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
		Booking booking=new Booking();
		booking.setPurpose(bookingDetails.getPurpose());
		booking.setStartTime(startTime);
		booking.setEndTime(endTime);
		booking.setBookedRoom(meetingRoom.getId());
		booking.setBookedBy(user.getId());
		booking.setStatus(Status.BOOKED);
		
		NewBooking newBooking=new NewBooking();
		newBooking.setStartDate(formateDate1);
		newBooking.setStartTime(startMinute);
		newBooking.setEndDate(formateDate2);
		newBooking.setEndTime(endMinute);
		newBooking.setBookedRoom(meetingRoom.getId());
		newBooking.setStatus(Status.BOOKED);
		
		booking.setNewBooking(newBooking);
		newBooking.setBooking(booking);
		bookingDao.save(booking,null);
		try {
			mailService.sendBookingConfirmationMail(user, meetingRoom, booking);
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
		return bookingDao.myBookings(id);	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return bookingDao.deleteById(id);
	}

}
