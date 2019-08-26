package com.meeting.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meeting.booking.constants.RestMappingUrls;
import com.meeting.booking.exceptions.ApplicationExceptions;
import com.meeting.booking.model.MeetingRoom;
import com.meeting.booking.pojo.RoomPojo;
import com.meeting.booking.pojo.RoomsVsAvailibility;
import com.meeting.booking.service.MeetingRoomService;
import com.meeting.booking.utils.RestResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController(value = RestMappingUrls.RoomsUrls.BASE)
public class MeetingRoomController {

	@Autowired
	private MeetingRoomService meetingRoomService;

	// Get all room
	@RequestMapping(value = RestMappingUrls.RoomsUrls.ADD_ROOM, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse<RoomPojo> add(@RequestBody RoomPojo roomPojo) throws ApplicationExceptions {
		RoomPojo meetingRooms = meetingRoomService.addRoom(roomPojo);
		return new RestResponse<RoomPojo>(false, "0", meetingRooms, "SUCCES");
	}

	// Get all room
	@RequestMapping(value = RestMappingUrls.RoomsUrls.GET_ALL_ROOMS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse<List<MeetingRoom>> getAll() {
		List<MeetingRoom> meetingRooms = meetingRoomService.getAll();
		if (CollectionUtils.isEmpty(meetingRooms))
			return new RestResponse<List<MeetingRoom>>(true, "1", meetingRooms, "FAILED");
		return new RestResponse<List<MeetingRoom>>(false, "0", meetingRooms, "SUCCES");
	}

	// get all room status by date
	@RequestMapping(value = RestMappingUrls.RoomsUrls.GET_ALL_DATE_WISE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse<List<RoomsVsAvailibility>> roomsVsAvailiblity(@RequestParam(required = true) String date) {
		List<RoomsVsAvailibility> roomsVsAvailibilities = meetingRoomService.roomsVsAvailiblity(date);
		if (CollectionUtils.isEmpty(roomsVsAvailibilities))
			return new RestResponse<List<RoomsVsAvailibility>>(false, "1", roomsVsAvailibilities, "FAILED");
		return new RestResponse<List<RoomsVsAvailibility>>(false, "0", roomsVsAvailibilities, "SUCCES");
	}
}
