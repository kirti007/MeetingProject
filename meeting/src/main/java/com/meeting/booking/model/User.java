package com.meeting.booking.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = User.FIELDS.TABLE_NAME)
public class User implements Serializable {
	public interface FIELDS {
		String TABLE_NAME = "user";
		String NAME = "name";
		String EMAIL_ID = "email_id";
		String PASSWORD = "password";
		String USER_NAME = "user_name";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = User.FIELDS.NAME, nullable = false)
	private String name;
	@Column(name = User.FIELDS.EMAIL_ID, nullable = false, unique = true)
	private String emailId;
	@Column(name = User.FIELDS.PASSWORD, nullable = false)
	private String password;
	@Column(name = User.FIELDS.USER_NAME, nullable = false, unique = true)
	private String userName;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String name, String emailId, String password, String userName) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
