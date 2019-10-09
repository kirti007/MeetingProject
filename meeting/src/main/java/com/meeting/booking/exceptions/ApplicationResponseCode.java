package com.meeting.booking.exceptions;

public enum ApplicationResponseCode {
	SUCCESS("SUCCESS", "0"),
	// Request failed
	FAILED("FAILED", "0"), COMMON("", ""),
	CHECK_AVAILIBILITY_INCOMPLETE_DATA("Select room or enter start date and end date to check availibility",
			"INC_DATA"), //
	ROOM_DATA_IS_NULL("Room data is null !!", "INV_REQ"), //
	USER_ID_NULL("User id is null !!", "UID_NULL"), //
	WROND_DATE_SELECTED("Starts date and time always be lesser then End date time !!", "WRG_DATE"), //
	INVALID_ROOM("Invalid room !!", "INV_ROOM"), //
	NOT_VALID_USER("Not valid user !!", "INV_USER"),
	ALREADY_BOOKED("Room is already booked between entered time","Wrong_time"),
	LONG_BOOKING("More than 10 hours booking in a single day is not allowed or Time can't be same","INV_Time"),
	MULTIPLE_DAY("START TIME IS ALWAYS LESS THAN END TIME","WRG_TIME"),
	SAME_TIME("Start Time & End Time Can Not Be Same", "INV_Time");
	private String message;
	private String code;

	private ApplicationResponseCode() {
		// TODO Auto-generated constructor stub
	}

	private ApplicationResponseCode(String message, String code) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static ApplicationResponseCode formatMessage(ApplicationResponseCode applicationResponseCode, String value) {
		if (applicationResponseCode != null) {
			ApplicationResponseCode commonResponseCode = ApplicationResponseCode.COMMON;
			commonResponseCode.setCode(applicationResponseCode.code);
			commonResponseCode.setMessage(String.format(applicationResponseCode.message, value));
			return commonResponseCode;
		}
		return applicationResponseCode;
	}
}
