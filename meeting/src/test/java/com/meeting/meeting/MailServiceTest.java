package com.meeting.meeting;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.meeting.booking.enums.Status;
import com.meeting.booking.model.Booking;
import com.meeting.booking.model.MeetingRoom;
import com.meeting.booking.model.User;
import com.meeting.booking.service.MailService;

public class MailServiceTest {

	@Test
	public void test() {
		MailService mailService = new MailService();
		User user = new User("Rahul", "bansalr225@gmail.com", "", "rb");
		assertEquals(true, mailService.sendOtp("OTP", user));
	}

	@Test
	public void testSendBookingConfirmationMail() {
		MailService mailService = new MailService();
		User user = new User("Rahul", "bansalr225@gmail.com", "", "rb");
		MeetingRoom meetingRoom = new MeetingRoom(122, "Luxury");
		Booking booking = new Booking(0, "For sleeping ", new Date(), new Date(), 122, 123, Status.BOOKED);
		assertEquals(true, mailService.sendBookingConfirmationMail(user, meetingRoom, booking));
	}

}
