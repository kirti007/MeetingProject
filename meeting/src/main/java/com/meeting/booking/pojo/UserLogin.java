package com.meeting.booking.pojo;

public class UserLogin {
	private String userNameOrEmail;
	private String password;

	public UserLogin() {
		// TODO Auto-generated constructor stub
	}

	public UserLogin(String userNameOrEmail, String password) {
		super();
		this.userNameOrEmail = userNameOrEmail;
		this.password = password;
	}

	public String getUserNameOrEmail() {
		return userNameOrEmail;
	}

	public void setUserNameOrEmail(String userNameOrEmail) {
		this.userNameOrEmail = userNameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
