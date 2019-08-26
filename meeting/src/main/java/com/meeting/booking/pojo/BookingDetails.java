package com.meeting.booking.pojo;

public class BookingDetails {
	private int userId;
	private int roomId;
	private String startTime;
	private String endTime;
	private String purpose;

	public BookingDetails() {
		// TODO Auto-generated constructor stub
	}

	public BookingDetails(int userId, int roomId, String startTime, String endTime, String p) {
		super();
		this.userId = userId;
		this.roomId = roomId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.purpose = p;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
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

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

}
