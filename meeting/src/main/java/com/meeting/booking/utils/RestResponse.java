package com.meeting.booking.utils;

public class RestResponse<T> {
	private boolean error;
	private String code;
	private T data;
	private String message;

	public RestResponse() {
		// TODO Auto-generated constructor stub
	}

	public RestResponse(boolean error, String code, T data, String message) {
		super();
		this.error = error;
		this.code = code;
		this.data = data;
		this.message = message;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
