package com.meeting.booking.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.meeting.booking.pojo.UpdatePassword;
import com.meeting.booking.pojo.UpdatePasswordNew;
import com.meeting.booking.service.UserService;
import com.meeting.booking.utils.RestResponse;
@Controller
@RequestMapping("/update")
public class UpdatePassword_new {
	@Autowired
	UserService userService;
	
	@PostMapping("/reset")
	public ModelAndView UpdatePasswordNew(UpdatePasswordNew updatePassword) {
		ModelAndView m=new ModelAndView("Success");

		userService.UpdatePasswordNew(updatePassword);
		return m;

	}

}
