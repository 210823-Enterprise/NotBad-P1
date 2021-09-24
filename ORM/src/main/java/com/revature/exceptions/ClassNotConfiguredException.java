package com.revature.exceptions;

public class ClassNotConfiguredException extends RuntimeException {
	
	private static final long serialVersionUID = -5449604552252364346L;

	public ClassNotConfiguredException() {
		super("Attempted to get meta model of a class that has not been added to the Configuration.");
	}
	
}
