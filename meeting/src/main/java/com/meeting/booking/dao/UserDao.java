package com.meeting.booking.dao;

import com.meeting.booking.model.User;

public interface UserDao {

	User findById(int userId);

	void save(User user);

	User getUser(String userNameOrEmail);

	void update(User user);
}
