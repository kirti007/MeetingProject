package com.meeting.booking.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.meeting.booking.dao.MeetingRoomDao;
import com.meeting.booking.exceptions.ApplicationExceptions;
import com.meeting.booking.exceptions.ApplicationResponseCode;
import com.meeting.booking.model.Booking;
import com.meeting.booking.model.MeetingRoom;
import com.meeting.booking.pojo.CheckAvailibilty;
import com.meeting.booking.pojo.RoomPojo;
import com.meeting.booking.pojo.RoomsVsAvailibility;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	@Autowired
	private MeetingRoomDao meetingRoomDao;
	@Autowired
	private BookingService bookingService;

	@Override
	public List<MeetingRoom> getAll() {
		return meetingRoomDao.findAll();
	}

	@Override
	public List<MeetingRoom> getAllAvailableRooms(CheckAvailibilty checkAvailibilty) throws ApplicationExceptions {
		// Object validation
		if (checkAvailibilty == null || StringUtils.isEmpty(checkAvailibilty.getStartTime())
				|| StringUtils.isEmpty(checkAvailibilty.getEndTime()))
			throw new ApplicationExceptions(ApplicationResponseCode.CHECK_AVAILIBILITY_INCOMPLETE_DATA);
		// Date validation
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = dateFormat.parse(checkAvailibilty.getStartTime());
			endTime = dateFormat.parse(checkAvailibilty.getEndTime());
		} catch (ParseException e) {
			return null;
		}
		if (startTime.after(endTime))
			throw new ApplicationExceptions(ApplicationResponseCode.WROND_DATE_SELECTED);
		// Check for single room
		if (checkAvailibilty.getRoomId() != null) {
			MeetingRoom meetingRoom = meetingRoomDao.findById(checkAvailibilty.getRoomId());
			if (meetingRoom == null)
				throw new ApplicationExceptions(ApplicationResponseCode.INVALID_ROOM);
			if (bookingService.isAvailableBetween(checkAvailibilty.getRoomId(), startTime, endTime))
				return Arrays.asList(meetingRoom);
			return null;
		} else {
			List<Booking> bookings = bookingService.bookingBetweenDate(startTime, endTime);
			if (CollectionUtils.isEmpty(bookings))
				return meetingRoomDao.findAll();
			Set<Integer> roomIds = bookings.stream().map(Booking::getBookedRoom).collect(Collectors.toSet());
			return meetingRoomDao.getAllRoomExcept(roomIds);
		}
	}

	@Override
	public List<RoomsVsAvailibility> roomsVsAvailiblity(String date) {
		if (StringUtils.isEmpty(date))
			return null;
		Date startTime = null;
		Date endTime = null;
		try {
			startTime = dateFormat.parse(date.concat(" 00:00"));
			endTime = dateFormat.parse(date.concat(" 23:59"));
		} catch (ParseException e) {
			return null;
		}
		List<MeetingRoom> meetingRooms = getAll();
		List<Booking> bookings = bookingService.bookingBetweenDate(startTime, endTime);
		if (CollectionUtils.isEmpty(bookings))
			return CollectionUtils.isEmpty(meetingRooms) ? null
					: meetingRooms.stream().map(room -> new RoomsVsAvailibility(room, null))
							.collect(Collectors.toList());
		Map<Integer, List<Booking>> roomIdVsBookings = new HashMap<Integer, List<Booking>>();
		for (Booking booking : bookings) {
			if (roomIdVsBookings.containsKey(booking.getBookedRoom()))
				roomIdVsBookings.get(booking.getBookedRoom()).add(booking);
			else {
				List<Booking> b = new ArrayList<Booking>();
				b.add(booking);
				roomIdVsBookings.put(booking.getBookedRoom(), b);
			}
		}
		return meetingRooms.stream()
				.map(room -> new RoomsVsAvailibility(room, getBlockedTime(roomIdVsBookings.get(room.getId()))))
				.collect(Collectors.toList());
	}

	private Map<String, String> getBlockedTime(List<Booking> list) {
		if (CollectionUtils.isEmpty(list))
			return null;
		Map<String, String> startVsEnd = new HashMap<>();
		list.forEach(b -> startVsEnd.put(dateFormat.format(b.getStartTime()), dateFormat.format(b.getEndTime())));
		return startVsEnd;
	}

	@Override
	public RoomPojo addRoom(RoomPojo roomPojo) throws ApplicationExceptions {
		if (roomPojo == null || StringUtils.isEmpty(roomPojo.getRoomName()))
			throw new ApplicationExceptions(ApplicationResponseCode.ROOM_DATA_IS_NULL);
		MeetingRoom meetingRoom = new MeetingRoom();
		meetingRoom.setRoomName(roomPojo.getRoomName());
		meetingRoomDao.save(meetingRoom);
		return roomPojo;
	}
}
