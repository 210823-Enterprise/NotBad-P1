package com.revature.exceptions;

public class UnsupportedTypeException extends RuntimeException {

	private static final long serialVersionUID = 2554385388448918222L;

	public UnsupportedTypeException() {
		super();
	}

	public UnsupportedTypeException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnsupportedTypeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UnsupportedTypeException(final String message) {
		super(message);
	}

	public UnsupportedTypeException(final Throwable cause) {
		super(cause);
	}
	
	

}
