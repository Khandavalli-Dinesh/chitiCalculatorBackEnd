package com.galvan.chiti.auxilliaries;

public class NoPrivilageException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NoPrivilageException() {
		super("User does not have privilage to perform this action");
	}
	
	public NoPrivilageException(String message) {
		super(message);
	}

}
