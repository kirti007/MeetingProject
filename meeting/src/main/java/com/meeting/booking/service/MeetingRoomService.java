package com.meeting.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meeting.booking.exceptions.ApplicationExceptions;
import com.meeting.booking.model.MeetingRoom;
import com.meeting.booking.pojo.CheckAvailibilty;
import com.meeting.booking.pojo.RoomPojo;
import com.meeting.booking.pojo.RoomsVsAvailibility;

@Service
public interface MeetingRoomService {

	List<MeetingRoom> getAll();

	List<MeetingRoom> getAllAvailableRooms(CheckAvailibilty checkAvailibilty) throws ApplicationExceptions;

	List<RoomsVsAvailibility> roomsVsAvailiblity(String date);

	RoomPojo addRoom(RoomPojo roomPojo) throws ApplicationExceptions;

}
