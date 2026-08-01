package com.cursobackend.aula6.domain.order.exception;

@SuppressWarnings("serial")
public class InvalidStateException extends RuntimeException{
	
	public InvalidStateException(String message) {
		super(message);
	}
}
