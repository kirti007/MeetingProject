package com.meeting.booking.dao;

import java.util.List;

import com.meeting.booking.model.User;
import com.meeting.booking.pojo.*;

public interface UserDao {

	User findById(int userId);

	void save(User user);

	User getUser(String userNameOrEmail);

	void update(User user);

	List<User> getUserId(String emailId);
	
	void UpdatePasswordNew(UpdatePasswordNew updatePassword);

	List<String> getAllEmail();
}
