package com.cursobackend.aula6.domain.orders.exception;

@SuppressWarnings("serial")
public class OrderNotFoundException extends RuntimeException {

	public OrderNotFoundException(String message) {
		super(message);
	}
	
}
