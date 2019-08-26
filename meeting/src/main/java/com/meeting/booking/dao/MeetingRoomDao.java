package com.meeting.booking.dao;

import java.util.List;
import java.util.Set;

import com.meeting.booking.model.MeetingRoom;

public interface MeetingRoomDao {

	List<MeetingRoom> findAll();

	MeetingRoom findById(int roomId);

	void save(MeetingRoom meetingRoom);

	List<MeetingRoom> getAllRoomExcept(Set<Integer> roomIds);
}
