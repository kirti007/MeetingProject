package com.meeting.booking.enums;

public enum Status {
	AVAILABLE, BOOKED, CANCELLED;

	public static boolean isExist(Status status) {
		for (Status s : Status.values())
			if (s == status)
				return true;
		return false;
	}
}
