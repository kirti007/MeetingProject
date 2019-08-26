package com.meeting.booking.pojo;

public class UpdatePassword {
	private String userNameOrEmail;
	private String oldPassword;
	private String newPassword;

	public UpdatePassword() {
		// TODO Auto-generated constructor stub
	}

	public UpdatePassword(String userNameOrEmail, String oldPassword, String newPassword) {
		super();
		this.userNameOrEmail = userNameOrEmail;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getUserNameOrEmail() {
		return userNameOrEmail;
	}

	public void setUserNameOrEmail(String userNameOrEmail) {
		this.userNameOrEmail = userNameOrEmail;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
