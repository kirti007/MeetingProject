package com.meeting.booking.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.meeting.booking.model.MeetingRoom;

@Transactional
@Repository
public class MeetingRoomDaoImpl implements MeetingRoomDao {
	@Autowired
	private EntityManager factory;

	@SuppressWarnings("unchecked")
	@Override
	public List<MeetingRoom> findAll() {
		Query p = factory.createNativeQuery("select * from meeting_rooms", MeetingRoom.class);
		return p.getResultList();
	}

	@Override
	public MeetingRoom findById(int roomId) {
		return factory.find(MeetingRoom.class, roomId);
	}

	@Override
	public void save(MeetingRoom meetingRoom) {
		factory.persist(meetingRoom);
	}

	@Override
	public List<MeetingRoom> getAllRoomExcept(Set<Integer> roomIds) {
		List<MeetingRoom> list = findAll();
		if (CollectionUtils.isEmpty(list))
			return null;
		Iterator<MeetingRoom> iterator = list.iterator();
		while (iterator.hasNext()) {
			MeetingRoom meetingRoom = (MeetingRoom) iterator.next();
			if (roomIds.contains(meetingRoom.getId()))
				iterator.remove();
		}
		return list;
	}
}
