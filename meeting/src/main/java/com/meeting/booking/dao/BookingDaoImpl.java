package com.meeting.booking.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.meeting.booking.enums.Status;
import com.meeting.booking.model.Booking;
import com.meeting.booking.model.MeetingRoom;
import com.meeting.booking.model.NewBooking;
import com.meeting.booking.pojo.ActiveBookingResponse;
import com.meeting.booking.pojo.AvailibilityResponse;
import com.meeting.booking.pojo.CheckAvailibilty;

@Transactional
@Repository
public class BookingDaoImpl implements BookingDao {
	@Autowired
	private EntityManager factory;

	@Override
	public void save(Booking booking, NewBooking booking2) {
//Booking b1=new Booking();
//b1.setNewBooking(booking2);
//NewBooking b2=new NewBooking();
//b2.setBooking(booking);

		factory.persist(booking);
//factory.persist(booking2);

	}

	@Override
	public boolean isAvailableBetween(int roomId, Date startTime, Date endTime) {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Booking> bookingBetweenDate(Date startTime, Date endTime) {
		String query = "SELECT * FROM meetings.booking b where b.start_time >= " + startTime + " AND b.end_time <= "
				+ endTime;
		Query p = factory.createNativeQuery(query, Booking.class);
		return p.getResultList();
	}

	@Override
	public List<Booking> getAll() {
		Query p = factory.createNativeQuery("select * from meetings.booking", Booking.class);
		@SuppressWarnings("unchecked")
		List<Booking> bookings = p.getResultList();
		if (CollectionUtils.isEmpty(bookings))
			return null;
		return bookings;
	}

	@SuppressWarnings({ "unchecked", "null" })
	public List<ActiveBookingResponse> getFromCurrentDate(int id) {

		/*
		 * SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date
		 * date = new Date(System.currentTimeMillis()); String s1="";
		 * s1="'"+formatter.format(date)+"'";
		 */
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String s1 = "";
		s1 = "'" + timeStamp + "'";

		Query q1 = factory.createNativeQuery(
				"SELECT * FROM meetings.booking b where b.end_time>=" + s1 + "and b.booked_room=" + id, Booking.class);
		List<Booking> bookings = q1.getResultList();
		List<ActiveBookingResponse> bookings2 = new ArrayList<ActiveBookingResponse>();
		Iterator<Booking> itr = bookings.iterator();

		if (CollectionUtils.isEmpty(bookings))
			return null;

		while (itr.hasNext()) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

			ActiveBookingResponse b1 = new ActiveBookingResponse();
			Booking b = itr.next();
			Date sTime = b.getStartTime();
			Date eTime = b.getEndTime();
			String[] s2 = sTime.toString().split(" ");
			String[] s3 = s2[0].split("-");
			String startDate = s3[2] + "/" + s3[1] + "/" + s3[0];
			String startTime = s2[1].substring(0, 5);

			String[] s5 = eTime.toString().split(" ");
			String[] s6 = s5[0].split("-");
			String endDate = s6[2] + "/" + s6[1] + "/" + s6[0];
			String endTime = s5[1].substring(0, 5);

			// String strDate1 = dateFormat.format(sTime).substring(0, 19).replace("T", "");
			// String strDate2 = dateFormat.format(eTime).substring(0, 19).replace("T", "");
			b1.setStartDate(startDate);
			b1.setStartTime(startTime);
			b1.setEndDate(endDate);
			b1.setEndTime(endTime);
			b1.setPurpose(b.getPurpose());
			bookings2.add(b1);

		}

		return bookings2;
	}

	@SuppressWarnings("unchecked")
	public boolean checkStatus(CheckAvailibilty request) {
		// TODO Auto-generated method stub
		boolean status = false;
		Integer formateDate1;
		Integer formateDate2;
		Integer startMinute;
		Integer endMinute;

		String[] splitStartDate = request.getStartTime().split(" ");
		String[] splitEndDate = request.getEndTime().split(" ");

		String[] SplitStartDate1 = splitStartDate[0].split("/");
		String[] SplitEndDate2 = splitEndDate[0].split("/");
		String[] splitStartTime = splitStartDate[1].split(":");
		String[] splitEndTime = splitEndDate[1].split(":");

		startMinute = (Integer.parseInt(splitStartTime[0]) * 60) + Integer.parseInt(splitStartTime[1]);
		endMinute = (Integer.parseInt(splitEndTime[0]) * 60) + Integer.parseInt(splitEndTime[1]);

		formateDate1 = Integer.parseInt(SplitStartDate1[2] + "" + SplitStartDate1[1] + "" + SplitStartDate1[0]);
		formateDate2 = Integer.parseInt(SplitEndDate2[2] + "" + SplitEndDate2[1] + "" + SplitEndDate2[0]);

		Query q = factory.createNativeQuery("SELECT * FROM  meetings.newbooking b where ( " + formateDate1
				+ " between b.start_date AND b.end_date or " + formateDate2 + " between b.start_date AND b.end_date or "
				+ formateDate1 + " <= b.start_date and " + formateDate2 + " >= b.end_date ) AND ( " + startMinute
				+ " between b.start_time AND b.end_time or " + endMinute + " between b.start_time AND b.end_time or "
				+ startMinute + " <= b.start_time and " + endMinute + " >= b.end_time ) and booked_room = "
				+ request.getRoomId(), NewBooking.class);

		List<NewBooking> list = q.getResultList();

		if (list.isEmpty()) {
			status = true;
		}
		return status;
	}

	@Override
	public List<AvailibilityResponse> myBookings(int id) {
		// TODO Auto-generated method stub
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String s1 = "";
		s1 = "'" + timeStamp + "'";

		Query q1 = factory.createNativeQuery(
				"SELECT * FROM meetings.booking b where b.end_time>=" + s1 + "and b.booked_by=" + id, Booking.class);
		List<Booking> bookings = q1.getResultList();
		List<AvailibilityResponse> bookings2 = new ArrayList<AvailibilityResponse>();
		Iterator<Booking> itr = bookings.iterator();

		if (CollectionUtils.isEmpty(bookings))
			return null;

		while (itr.hasNext()) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

			AvailibilityResponse b1 = new AvailibilityResponse();
			Booking b = itr.next();
			Date sTime = b.getStartTime();
			Date eTime = b.getEndTime();

			String[] s2 = sTime.toString().split(" ");
			String[] s3 = s2[0].split("-");
			String startDate = s3[2] + "/" + s3[1] + "/" + s3[0];
			String starTime = s2[1].substring(0, 5);

			String[] s5 = eTime.toString().split(" ");
			String[] s6 = s5[0].split("-");
			String endDate = s6[2] + "/" + s6[1] + "/" + s6[0];
			String endTime = s5[1].substring(0, 5);
			int k = b.getBookedRoom();
			Query q2 = factory.createNativeQuery("select room_name from meeting_rooms where id=" + k);
			String room = (String) q2.getSingleResult();

			b1.setStartDate(startDate);
			b1.setStartTime(starTime);
			b1.setEndDate(endDate);
			b1.setEndTime(endTime);
			b1.setPurpose(b.getPurpose());
			b1.setId(b.getId());
			b1.setMeetingRoom(room);
			bookings2.add(b1);

		}

		return bookings2;

	}

	@Override
	public boolean deleteById(int id) {
		boolean b = false;
		Query q2 = factory.createNativeQuery("delete from newbooking where booking_id=" + id);
		Query q1 = factory.createNativeQuery("delete from booking where id=" + id);

		int i = q2.executeUpdate();
		int j = q1.executeUpdate();
		if (i >= 1 && j >= 1) {
			b = true;
		}

		return b;
	}

}
