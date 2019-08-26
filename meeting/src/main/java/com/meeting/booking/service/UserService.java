package com.meeting.booking.service;

import org.springframework.stereotype.Service;

import com.meeting.booking.pojo.UpdatePassword;
import com.meeting.booking.pojo.UserLogin;
import com.meeting.booking.pojo.UserPojo;

@Service
public interface UserService {

	boolean signUp(UserPojo userPojo);

	UserPojo login(UserLogin login);

	boolean updatePassword(UpdatePassword updatePassword);

	boolean forgetPassword(String userId);

}
