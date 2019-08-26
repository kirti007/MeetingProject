package com.meeting.booking.pojo;

public class RoomPojo {
	private int id;
	private String roomName;

	public RoomPojo() {
		// TODO Auto-generated constructor stub
	}

	public RoomPojo(int id, String roomName) {
		super();
		this.id = id;
		this.roomName = roomName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

}
