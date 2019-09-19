package com.meeting.booking.pojo;

public class ActiveBookingResponse {

	// private String startDate;
	// private String endDate;
	private String start;
	private String end;
	private String purpose;
	private String dow = null;
	private Ranges[] ranges;

	public Ranges[] getRanges() {
		return ranges;
	}

	public void setRanges(Ranges[] ranges) {
		this.ranges = ranges;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDow() {
		return dow;
	}

	public void setDow(String dow) {
		this.dow = dow;
	}

	/*
	 * public String getStartDate() { return startDate; }
	 * 
	 * public void setStartDate(String startDate) { this.startDate = startDate; }
	 */

}
