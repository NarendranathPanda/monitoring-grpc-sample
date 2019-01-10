package com.naren.grpc.metric.util;

public class InvalidLableValuePairException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String MESSAGE = "Invalid lable value pairs.";

	public InvalidLableValuePairException() {
		super(MESSAGE);
	}

	public InvalidLableValuePairException(String message) {
		super(message);
	}

	public InvalidLableValuePairException(Throwable cause) {
		super(cause);
	}

	public InvalidLableValuePairException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidLableValuePairException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
