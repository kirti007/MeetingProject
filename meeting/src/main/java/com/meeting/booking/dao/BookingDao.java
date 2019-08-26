package com.meeting.booking.dao;

import java.util.Date;
import java.util.List;

import com.meeting.booking.model.Booking;
import com.meeting.booking.model.NewBooking;
import com.meeting.booking.pojo.ActiveBookingResponse;
import com.meeting.booking.pojo.AvailibilityResponse;
import com.meeting.booking.pojo.CheckAvailibilty;

public interface BookingDao {

	public void save(Booking booking, NewBooking newbooking);

	boolean isAvailableBetween(int roomId, Date startTime, Date endTime);

	List<Booking> bookingBetweenDate(Date startTime, Date endTime);

	List<Booking> getAll();

	public List<ActiveBookingResponse> getFromCurrentDate(int id);

	public boolean checkStatus(CheckAvailibilty request);
	
	public List<AvailibilityResponse> myBookings(int id);
	
	public boolean deleteById(int id);

}
