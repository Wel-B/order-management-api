package com.cursobackend.aula6.domain.user.exception;

@SuppressWarnings("serial")
public class DuplicateEmailException extends RuntimeException {

	public DuplicateEmailException(String message) {
		super(message);
	}
}
