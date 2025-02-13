package com.order.brunoestevam.exception;

import java.util.Date;

public class Error {
	private int status;

	private String uid;

	private String message;
	
	private String exceptionClass;

	private Date date;
	
	public Error() {
	}

	public Error(int status, String uid, String message, String exceptionClass,
			Date date) {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}