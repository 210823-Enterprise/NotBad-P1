package com.revature.exceptions;

public class ClassNotAnnotatedException extends RuntimeException {

	private static final long serialVersionUID = -7405469246823420442L;

	public ClassNotAnnotatedException() {
		super();
	}

	public ClassNotAnnotatedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ClassNotAnnotatedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ClassNotAnnotatedException(final String message) {
		super(message);
	}

	public ClassNotAnnotatedException(final Throwable cause) {
		super(cause);
	}

}
