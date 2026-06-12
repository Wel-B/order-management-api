package com.cursobackend.aula6.domain.user.exception;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends RuntimeException{

	public InvalidCredentialsException(String message) {
		super(message);
	}
	
}
