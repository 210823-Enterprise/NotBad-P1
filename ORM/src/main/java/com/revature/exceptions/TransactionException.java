package com.revature.exceptions;

public class TransactionException extends RuntimeException {

	private static final long serialVersionUID = 5752139326004240221L;

	public TransactionException() {
		super();
	}

	public TransactionException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TransactionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TransactionException(final String message) {
		super(message);
	}

	public TransactionException(final Throwable cause) {
		super(cause);
	}

	
}
