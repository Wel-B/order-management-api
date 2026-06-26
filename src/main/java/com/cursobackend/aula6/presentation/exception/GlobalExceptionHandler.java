package com.cursobackend.aula6.presentation.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cursobackend.aula6.application.orders.dto.ExceptionErrorResponse;
import com.cursobackend.aula6.application.orders.dto.FieldValidationErrorResponse;
import com.cursobackend.aula6.application.orders.dto.ValidationErrorResponse;
import com.cursobackend.aula6.domain.orders.exception.ForbiddenActionException;
import com.cursobackend.aula6.domain.orders.exception.InvalidStateException;
import com.cursobackend.aula6.domain.orders.exception.OrderNotFoundException;
import com.cursobackend.aula6.domain.user.exception.DuplicateEmailException;
import com.cursobackend.aula6.domain.user.exception.InvalidCredentialsException;
import com.cursobackend.aula6.domain.user.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ExceptionErrorResponse> handleOrderNotFound(OrderNotFoundException e) {
		
		log.warn("Order not found: {}", e.getMessage());
		
		ExceptionErrorResponse error = new ExceptionErrorResponse(
				"ORDER_NOT_FOUND", e.getMessage(), LocalDateTime.now()
				);
		
		return ResponseEntity.status(404).body(error);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionErrorResponse> handleUserNotFound(UserNotFoundException e) {
		
		ExceptionErrorResponse error = new ExceptionErrorResponse(
				"USER_NOT_FOUND", e.getMessage(), LocalDateTime.now()
		);
		
		return ResponseEntity.status(404).body(error);
	}

	@ExceptionHandler(InvalidStateException.class)
	public ResponseEntity<ExceptionErrorResponse> handleBusiness(InvalidStateException e) {
		
		log.warn("Invalid state: {}", e.getMessage());
		
		ExceptionErrorResponse error = new ExceptionErrorResponse(
				"INVALID_STATE", e.getMessage(), LocalDateTime.now()
				);
		
		return ResponseEntity.status(400).body(error);
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ExceptionErrorResponse> handleInvalidCredentials(InvalidCredentialsException e) {
		
		log.warn("Invalid credential {}", e.getMessage());
		
		ExceptionErrorResponse error = new ExceptionErrorResponse(
				"INVALID_CREDENTIAL", e.getMessage(), LocalDateTime.now()
				);
		
		return ResponseEntity.status(401).body(error);
	}
	
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<ExceptionErrorResponse> handleDuplicateEmail(DuplicateEmailException e) {
		
		ExceptionErrorResponse error = new ExceptionErrorResponse(
				"DUPLICATED_EMAIL", e.getMessage(), LocalDateTime.now()
		);
		
		return ResponseEntity.status(400).body(error);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionErrorResponse> handleGeneric(Exception e) {
		
		log.error("Internal error", e);
		
		ExceptionErrorResponse error = new ExceptionErrorResponse(
				"INTERNAL_ERROR", "Ocorreu um erro inesperado", LocalDateTime.now()
				);
		
		return ResponseEntity.status(500).body(error);
	}
	
	@ExceptionHandler(ForbiddenActionException.class)
	public ResponseEntity<ExceptionErrorResponse> handleForbiddenAction(ForbiddenActionException e) {
		
		ExceptionErrorResponse error = new ExceptionErrorResponse(
				"ACCESS_DENIED", e.getMessage(), LocalDateTime.now()
		);
		
		return ResponseEntity.status(403).body(error);
	}
	
	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	public ResponseEntity<ExceptionErrorResponse> handleAccessDenied(Exception e) {
		
		ExceptionErrorResponse error = new ExceptionErrorResponse(
				"ACCESS_DENIED", "Sem permissão", LocalDateTime.now()
		);
		
		return ResponseEntity.status(403).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationError(MethodArgumentNotValidException e) {
		
		log.warn("validation error: {}", e.getMessage());
		
		List<FieldValidationErrorResponse> fields = e.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> new FieldValidationErrorResponse(
						error.getField(),
						error.getDefaultMessage()
				))
				.toList();
		
		ValidationErrorResponse error = new ValidationErrorResponse(
				"VALIDATION_ERROR", fields, LocalDateTime.now()
				);
		
		return ResponseEntity.status(400).body(error);
	}
	
}
