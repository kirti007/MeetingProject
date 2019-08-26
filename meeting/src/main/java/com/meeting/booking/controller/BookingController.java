package com.meeting.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meeting.booking.constants.RestMappingUrls;
import com.meeting.booking.exceptions.ApplicationExceptions;
import com.meeting.booking.model.Booking;
import com.meeting.booking.model.MeetingRoom;
import com.meeting.booking.pojo.ActiveBookingResponse;
import com.meeting.booking.pojo.AvailibilityResponse;
import com.meeting.booking.pojo.BookingDetails;
import com.meeting.booking.pojo.CheckAvailibilty;
import com.meeting.booking.service.BookingService;
import com.meeting.booking.service.MeetingRoomService;
import com.meeting.booking.utils.RestResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController(value = RestMappingUrls.Booking.BASE)
public class BookingController {

	@Autowired
	private BookingService bookingService;
	@Autowired
	private MeetingRoomService meetingRoomService;

	// For check availability of room
	@RequestMapping(value = RestMappingUrls.Booking.CHECK_AVAILIBILITY, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse<List<MeetingRoom>> checkAvailability(@RequestBody CheckAvailibilty checkAvailibilty)
			throws ApplicationExceptions {
		List<MeetingRoom> meetingRooms = meetingRoomService.getAllAvailableRooms(checkAvailibilty);
		if (meetingRooms == null || meetingRooms.isEmpty())
			return new RestResponse<List<MeetingRoom>>(true, "1", meetingRooms, "FAILED");
		return new RestResponse<List<MeetingRoom>>(false, "0", meetingRooms, "SUCCESS");
	}

	// Book room
	@RequestMapping(value = RestMappingUrls.Booking.BOOK, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse<String> book(@RequestBody BookingDetails bookingDetails) throws ApplicationExceptions {
		if (bookingService.book(bookingDetails))
			return new RestResponse<String>(false, "0", "Room has been booked ", "SUCCESS");
		return new RestResponse<String>(true, "0", "Unable to book the room  ", "FAILED");
	}

	@RequestMapping(value = RestMappingUrls.Booking.getFromCurrentDate, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse<List<ActiveBookingResponse>> getFromCurrentDate(@RequestParam Integer id) {
		List<ActiveBookingResponse> b = null;
		b = bookingService.getFromCurrentDate(id);

		{
			return new RestResponse<List<ActiveBookingResponse>>(true, "0", b, "SUCCESS");
		}

	}

	@RequestMapping(value = RestMappingUrls.Booking.myBookings, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse<List<AvailibilityResponse>> myBookings(@RequestParam int id) {
		List<AvailibilityResponse> b1 = null;
		b1 = bookingService.myBookings(id);
		{
			return new RestResponse<List<AvailibilityResponse>>(false, "0", b1, "SUCCESS");
		}
	}

	@RequestMapping(value = RestMappingUrls.Booking.deleteById, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse<String> deleteById(@RequestParam int id) {
		if (bookingService.deleteById(id))
			return new RestResponse<String>(false, "0", "Booking has been Canceled Successfully  ", "SUCCESS");
		return new RestResponse<String>(true, "0", "Unable to Cancel the Booking  ", "FAILED");
	}
}
