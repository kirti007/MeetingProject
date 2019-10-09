package com.meeting.booking.pojo;

import org.springframework.beans.factory.annotation.Autowired;

import com.meeting.booking.service.MailService;

public class UpdatePasswordNew {
	
	private String userNameOrEmail;
	private String newPassword;
	private int id;
	
	public UpdatePasswordNew() {
		// TODO Auto-generated constructor stub
	}

	public UpdatePasswordNew(String userNameOrEmail, String newPassword, int id) {
		super();
		this.userNameOrEmail = userNameOrEmail;
		this.newPassword = newPassword;
		this.id = id;
	}

	public String getUserNameOrEmail() {
		return userNameOrEmail;
	}

	public void setUserNameOrEmail(String userNameOrEmail) {
		this.userNameOrEmail = userNameOrEmail;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UpdatePasswordNew [userNameOrEmail=" + userNameOrEmail + ", newPassword=" + newPassword + ", id=" + id
				+ "]";
	}
	
	
}
