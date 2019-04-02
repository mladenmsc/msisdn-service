package com.vodafone.msisdns;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApplicationException(String message) {
		super("Application Exception: " + message);
	}

}
