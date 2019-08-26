package com.meeting.booking.pojo;

public class AvailibilityResponse {
   private String meetingRoom;
	public String getMeetingRoom() {
	return meetingRoom;
}

public void setMeetingRoom(String meetingRoom) {
	this.meetingRoom = meetingRoom;
}

	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String purpose;
	private int id;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AvailibilityResponse() {
		super();
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
