package com.meeting.booking.pojo;

public class CheckAvailibilty {
	private Integer roomId;// Can be null
	private String startTime;
	private String endTime;

	public CheckAvailibilty() {
		// TODO Auto-generated constructor stub
	}

	public CheckAvailibilty(Integer roomId, String startTime, String endTime) {
		super();
		this.roomId = roomId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
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

}
