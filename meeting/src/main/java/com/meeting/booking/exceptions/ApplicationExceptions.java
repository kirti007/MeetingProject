package com.meeting.booking.exceptions;

public class ApplicationExceptions extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String detailMessage;

	public ApplicationExceptions() {
		// TODO Auto-generated constructor stub
	};

	public ApplicationExceptions(String message, String code, String detailMessage) {
		super(message);
		this.code = code;
		this.detailMessage = detailMessage;
	}

	public ApplicationExceptions(String message, String code) {
		super(message);
		this.code = code;
	}

	public ApplicationExceptions(ApplicationResponseCode applicationResponseCode) {
		super(applicationResponseCode.getMessage());
		this.code = applicationResponseCode.getCode();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

}
