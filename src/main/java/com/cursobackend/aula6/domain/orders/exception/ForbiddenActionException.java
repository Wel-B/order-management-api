package com.cursobackend.aula6.domain.orders.exception;

@SuppressWarnings("serial")
public class ForbiddenActionException extends RuntimeException {

	public ForbiddenActionException(String message) {
		super(message);
	}
}
