package com.order.brunoestevam.exception;

import java.time.LocalDateTime;

public class ApiError {
	private int status;

	private String uid;

	private String message;
	
	private String exceptionClass;

	private LocalDateTime date;

	public ApiError(int status, String uid, String message, String exceptionClass,
			LocalDateTime date) {
		this.status = status;
		this.uid = uid;
		this.message = message;
		this.exceptionClass = exceptionClass;
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMessages() {
		return message;
	}

	public void setMessages(String message) {
		this.message = message;
	}

	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}