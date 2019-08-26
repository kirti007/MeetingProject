package com.meeting.user;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.meeting.booking.model.User;
import com.meeting.booking.service.MailService;
import com.meeting.booking.service.UserServiceImpl;

public class UserServiceImplTest {

	@Test
	public void testSignUp() {
	}

	@Test
	public void testLogin() {
	}

	@Test
	public void testUpdatePassword() {
	}

	@Test
	public void testForgetPassword() {
	}

	@Test
	public void testComposeMailAndSend() {
		MailService mailService = new MailService();
		UserServiceImpl implServiceImpl = new UserServiceImpl(mailService);
		User user = new User("Rahul", "bansalr225@gmail.com", "", "rb");
		assertEquals(true, implServiceImpl.composeMailAndSend(user));
	}

}
