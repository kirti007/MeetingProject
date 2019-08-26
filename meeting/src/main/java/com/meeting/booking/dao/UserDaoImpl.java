package com.meeting.booking.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.meeting.booking.model.User;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private EntityManager factory;

	@Override
	public User findById(int userId) {
		return factory.find(User.class, userId);
	}

	@Override
	public void save(User user) {
		factory.persist(user);
	}

	@Override
	public User getUser(String userNameOrEmail) {
		Query p = factory.createNativeQuery("select * from meetings.user", User.class);
		@SuppressWarnings("unchecked")
		List<User> users = p.getResultList();
		if (CollectionUtils.isEmpty(users))
			return null;
		return users.stream()
				.filter(u -> u.getEmailId().equals(userNameOrEmail) || u.getUserName().equals(userNameOrEmail))
				.findAny().orElse(null);
	}

	@Override
	public void update(User user) {
		factory.persist(user);

	}
}
