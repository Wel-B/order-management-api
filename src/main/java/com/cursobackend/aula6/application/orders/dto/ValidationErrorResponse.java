package com.cursobackend.aula6.application.orders.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(
		String error, List<FieldValidationErrorResponse> fields, LocalDateTime timesTamp) {
}
