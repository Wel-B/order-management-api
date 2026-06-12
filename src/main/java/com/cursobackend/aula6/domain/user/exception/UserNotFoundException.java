package com.cursobackend.aula6.domain.user.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
