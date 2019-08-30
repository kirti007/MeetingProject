 package com.meeting.booking.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootExceptionReporter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.meeting.booking")
@EntityScan(basePackages = { "com.meeting.booking.model" })
public class MeetingApplication extends SpringBootServletInitializer {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MeetingApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MeetingApplication.class, args);
	}

}
