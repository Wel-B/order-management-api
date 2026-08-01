package com.cursobackend.aula6.application.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(
		String error, List<FieldValidationErrorResponse> fields, LocalDateTime timesTamp) {
}
