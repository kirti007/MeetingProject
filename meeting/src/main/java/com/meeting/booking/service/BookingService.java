package com.meeting.booking.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.meeting.booking.exceptions.ApplicationExceptions;
import com.meeting.booking.model.Booking;
import com.meeting.booking.pojo.ActiveBookingResponse;
import com.meeting.booking.pojo.AvailibilityResponse;
import com.meeting.booking.pojo.BookingDetails;

@Service
public interface BookingService {

	public boolean book(BookingDetails bookingDetails) throws ApplicationExceptions;

	public boolean isAvailableBetween(int roomId, Date startTime, Date endTime);

	public List<Booking> bookingBetweenDate(Date startTime, Date endTime);

	public List<ActiveBookingResponse> getFromCurrentDate(int id);

	public List<AvailibilityResponse> myBookings(int id);

	public boolean deleteById(int id);

}
