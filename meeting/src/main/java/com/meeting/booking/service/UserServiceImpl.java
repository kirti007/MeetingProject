package com.meeting.booking.service;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.meeting.booking.dao.UserDao;
import com.meeting.booking.model.User;
import com.meeting.booking.pojo.UpdatePassword;
import com.meeting.booking.pojo.UserLogin;
import com.meeting.booking.pojo.*;

@Service
public class UserServiceImpl implements UserService {
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static int LENGTH_OF_OTP = 10;

	@Autowired
	private UserDao userDao;
	@Autowired
	private MailService mailService;

	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public UserServiceImpl(MailService mailService2) {
		this.mailService = mailService2;
	}

	@Override
	public boolean signUp(UserPojo userPojo) {
		if (userPojo == null)
			return false;
		String [] a=userPojo.getEmailId().split("@");
	    if(!a[1].equals("atmecs.com")) {
			return false;
		}
		String hashedPassword = hashPassword(userPojo.getPassword());
		User user = new User(userPojo.getName(), userPojo.getEmailId(), hashedPassword);
		userDao.save(user);
		return mailService.sendSignUpConfirmationMail(user);
	}

	private String hashPassword(String password) {
		if (StringUtils.isEmpty(password))
			return null;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	@Override
	public UserPojo login(UserLogin login) {
		User user = userDao.getUser(login.getUserNameOrEmail());
		if (user == null)
			throw new IllegalAccessError("Entered email is wrong !!");
		if (!validatePassword(login.getPassword(), user.getPassword()))
			throw new IllegalAccessError("Entered password is wrong !!");
		return new UserPojo(user.getId(), user.getName(), user.getEmailId(), null);
	}

	private static boolean validatePassword(String enteredPassword, String savedPassword) {
		if (StringUtils.isEmpty(savedPassword) || StringUtils.isEmpty(enteredPassword))
			return false;
		return BCrypt.checkpw(enteredPassword, savedPassword);
	}

	@Override
	public boolean updatePassword(UpdatePassword updatePassword) {
		if (updatePassword == null || StringUtils.isEmpty(updatePassword.getOldPassword())
				|| StringUtils.isEmpty(updatePassword.getNewPassword()))
			throw new IllegalAccessError("Please enter the data ");
		User user = userDao.getUser(updatePassword.getUserNameOrEmail());
		if (user == null)
			throw new IllegalAccessError("Entered email/userName is wrong !!");
		if (!validatePassword(updatePassword.getOldPassword(), user.getPassword()))
			throw new IllegalAccessError("Entered password is wrong !!");
		String hashedNewPassword = hashPassword(updatePassword.getNewPassword());
		user.setPassword(hashedNewPassword);
		userDao.update(user);
		return true;
	}

	@Override
	public boolean forgetPassword(String userId) {
		if (StringUtils.isEmpty(userId))
			throw new IllegalAccessError("Please enter email Id/user name ");
		User user = userDao.getUser(userId);
		if (user == null)
			throw new IllegalAccessError("Entered email/userName is wrong !!");
		return composeMailAndSend(user);
	}

	public boolean composeMailAndSend(User user) {
		StringBuilder builder = new StringBuilder();
		int a=LENGTH_OF_OTP;
		while (a-- != 0){
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		String OTP = builder.toString();
		boolean isSent = mailService.sendOtp(OTP, user);
		if (isSent) {
			user.setPassword(hashPassword(OTP));
			userDao.update(user);
			return true;
		} else
			return false;
	}
	
	@Override
	public boolean ForgotPassword_new(String emailId) {
	if (StringUtils.isEmpty(emailId))
	throw new IllegalAccessError("Please enter email Id ");
	User user = userDao.getUser(emailId);
	if (user == null)
	throw new IllegalAccessError("Entered email/userName is wrong !!");
	return mailService.sendLink(user);
	}
	
	@Override
	public boolean UpdatePasswordNew(UpdatePasswordNew updatePassword) {

	User user = userDao.getUser(updatePassword.getUserNameOrEmail());
	if (user == null)
	throw new IllegalAccessError("Entered email/userName is wrong !!");

	String hashedNewPassword = hashPassword(updatePassword.getNewPassword());
	// user.setPassword(hashedNewPassword);
	updatePassword.setNewPassword(hashedNewPassword);
	userDao.UpdatePasswordNew(updatePassword);
	return true;
	}
}
