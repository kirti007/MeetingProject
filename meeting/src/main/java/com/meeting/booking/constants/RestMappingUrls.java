package com.meeting.booking.constants;

public interface RestMappingUrls {
	String APP_BASE = "";
	String REQUEST = "/request";
	String RESPONSE = "/response";
	String BATCH = "/batch";

	interface RoomsUrls {
		String BASE = APP_BASE + "/meetings";
		String ADD_ROOM = BASE + "/add";
		String GET_ALL_ROOMS = BASE + "/getAll";
		String GET_ALL_DATE_WISE = BASE + "/dateWise";
	}

	interface User {
		String BASE = APP_BASE + "/user";
		String SIGN_UP = BASE + "/signUp";
		String LOGIN = BASE + "/login";
		String UPDATE_PASSWORD = BASE + "/updatePassword";
		String FORWARD_PASSWORD = BASE + "/forgetPassword";
	}

	interface Booking {
		String BASE = APP_BASE + "/bookings";
		String CHECK_AVAILIBILITY = BASE + "/checkAvailability";
		String BOOK = BASE + "/bookRoom";
		String getFromCurrentDate = BASE + "/getFromCurrentDate";
		String myBookings = BASE + "/myBookings";
		String deleteById = BASE + "/deleteById";

	}
}
