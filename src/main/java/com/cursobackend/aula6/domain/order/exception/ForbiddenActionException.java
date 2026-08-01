package com.cursobackend.aula6.domain.order.exception;

@SuppressWarnings("serial")
public class ForbiddenActionException extends RuntimeException {

	public ForbiddenActionException(String message) {
		super(message);
	}
}
