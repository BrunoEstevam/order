package com.order.brunoestevam.exception;

public class InvalidDataException extends RuntimeException {

	private static final long serialVersionUID = 4710149818049348529L;

	public InvalidDataException(String errorMessage) {
		super(errorMessage);
	}
}