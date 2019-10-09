package com.meeting.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequestMapping("/")
public class JSPController {
	
	@RequestMapping("/Reset")
	public String Landingpage()
	{
		return "Reset";
	}
	
	@RequestMapping("/Success")
	public String SuccessPage()
	{
		return "Success";
	}
	}

