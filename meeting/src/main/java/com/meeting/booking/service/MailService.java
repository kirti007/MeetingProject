package com.meeting.booking.service;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.meeting.booking.model.Booking;
import com.meeting.booking.model.MeetingRoom;
import com.meeting.booking.model.User;

@Service
public class MailService {
	private Logger logger = Logger.getLogger(MailService.class.getName());

	private static final String GMAIL_HOST = "smtp.gmail.com";
	private static final String TLS_PORT = "465";

	private static final String SENDER_EMAIL = "bookmeeting6@gmail.com";
	private static final String SENDER_USERNAME = "bookmeeting6@gmail.com";
	private static final String SENDER_PASSWORD = "Rahul@95";
	private static Properties props;
	private static Session session;

	private static void setPropertiesAndSession() {
		// protocol properties
		props = System.getProperties();
		props.setProperty("mail.smtps.host", GMAIL_HOST);
		props.setProperty("mail.smtp.port", TLS_PORT);
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtps.auth", "true");
		// close connection upon quit being sent
		props.put("mail.smtps.quitwait", "false");
		session = Session.getInstance(props, null);
	}

	public boolean sendOtp(String otp, User user) {
		setPropertiesAndSession();
		try {
			// create the message
			final MimeMessage msg = new MimeMessage(session);
			// set recipients and content
			msg.setFrom(new InternetAddress(SENDER_EMAIL));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmailId(), false));
			msg.setSubject("Reset password OTP ");
			msg.setText(
					"<b>Hi " + user.getName().toUpperCase()+ "</b> <br><br>\n         You have recently requested for a new password for your <b>connect2Conference</b> account. Your temporary password is :-<b> <mark>" + otp + "</mark></b>\n",
					"utf-8", "html");
			msg.setSentDate(new Date());
			// this means you do not need socketFactory properties
			Transport transport = session.getTransport("smtps");
			// send the mail
			transport.connect(GMAIL_HOST, SENDER_USERNAME, SENDER_PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			return true;
		} catch (MessagingException e) {
			logger.log(Level.SEVERE, "Failed to send message", e);
		}
		return false;
	}

	public boolean sendBookingConfirmationMail(User user, MeetingRoom meetingRoom, Booking booking) {
		setPropertiesAndSession();
		try {
			// create the message
			final MimeMessage msg = new MimeMessage(session);
			// set recipients and content
			msg.setFrom(new InternetAddress(SENDER_EMAIL));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmailId(), false));
			msg.setSubject("Your booking " + " for room " + booking.getBookedRoom() + " is " + booking.getStatus());
			msg.setText("Dear " + user.getName() + "\n           Your booked room is : " + meetingRoom.getRoomName()
					+ ",\n from - " + booking.getStartTime() + " to - " + booking.getEndTime()
					+ ".\n Purpose of booking is '" + booking.getPurpose() + "'", "utf-8", "html");
			msg.setSentDate(new Date());
			// this means you do not need socketFactory properties
			Transport transport = session.getTransport("smtps");
			// send the mail
			transport.connect(GMAIL_HOST, SENDER_USERNAME, SENDER_PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			return true;
		} catch (MessagingException e) {
			logger.log(Level.SEVERE, "Failed to send message", e);
		}
		return false;
	}
}
