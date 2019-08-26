package com.meeting.booking.pojo;

import java.util.Map;
import com.meeting.booking.model.MeetingRoom;

public class RoomsVsAvailibility {
	private MeetingRoom room;
	private Map<String, String> startVsEndTime;

	public RoomsVsAvailibility() {
		// TODO Auto-generated constructor stub
	}

	public RoomsVsAvailibility(MeetingRoom room, Map<String, String> blockedTime) {
		super();
		this.room = room;
		this.startVsEndTime = blockedTime;
	}

	public MeetingRoom getRoom() {
		return room;
	}

	public void setRoom(MeetingRoom room) {
		this.room = room;
	}

	public Map<String, String> getStartVsEndTime() {
		return startVsEndTime;
	}

	public void setStartVsEndTime(Map<String, String> startVsEndTime) {
		this.startVsEndTime = startVsEndTime;
	}

}
