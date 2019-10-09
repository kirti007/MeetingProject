package com.meeting.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meeting.booking.constants.RestMappingUrls;
import com.meeting.booking.pojo.UpdatePassword;
import com.meeting.booking.pojo.UserLogin;
import com.meeting.booking.pojo.UserPojo;
import com.meeting.booking.service.UserService;
import com.meeting.booking.utils.RestResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController(RestMappingUrls.User.BASE)
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = RestMappingUrls.User.SIGN_UP, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse<String> signUp(@RequestBody UserPojo userPojo) {
		boolean signUpDone = userService.signUp(userPojo);
		if (signUpDone)
			return new RestResponse<String>(false, "0",
					"Successfully registered, please login using userName/email id and password !!", "SUCCESS");
		return new RestResponse<String>(true, "0", "Please use Atmecs Email !!", "SUCCESS");
	}

	@RequestMapping(value = RestMappingUrls.User.LOGIN, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse<UserPojo> signUp(@RequestBody UserLogin login) {
		UserPojo userPojo = userService.login(login);
		if (userPojo == null)
			return new RestResponse<UserPojo>(true, "0", userPojo, "FAILED");
		return new RestResponse<UserPojo>(false, "0", userPojo, "SUCCESS");
	}

	// This is for user is login and trying to update password and can use for reset
	// password.
	@RequestMapping(value = RestMappingUrls.User.UPDATE_PASSWORD, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RestResponse<String> signUp(@RequestBody UpdatePassword updatePassword) {
		boolean chaged = userService.updatePassword(updatePassword);
		if (chaged)
			return new RestResponse<String>(false, "0", "Password has been set, please login again !!", "SUCCESS");
		return new RestResponse<String>(true, "0", "Unable to change the password", "FAILED");
	}

	// This is for forget password, send userName/email id in parameter.
	@RequestMapping(value = RestMappingUrls.User.FORWARD_PASSWORD, method = RequestMethod.GET)
	public RestResponse<String> signUp(@RequestParam String emailId) {
		boolean mailSent = userService.forgetPassword(emailId);
		if (mailSent)
			return new RestResponse<String>(false, "0",
					"One mail has been sent on your registered email id with one time password !!", "SUCCESS");
		return new RestResponse<String>(true, "0", "Oops.. some things is not right !!", "FAILED");
	}
	
	@RequestMapping(value = RestMappingUrls.User.ForgotPassword_new, method = RequestMethod.GET)
	public RestResponse<String> ForgotPassword_new(@RequestParam String emailId) {
	boolean mailSent = userService.ForgotPassword_new(emailId);
	if (mailSent)
	return new RestResponse<String>(false, "0",
	"Reset Password mail Link has been sent to your Registered mail id !!", "SUCCESS");
	return new RestResponse<String>(true, "0", "Oops.. some things is not right !!", "FAILED");
	}
}
