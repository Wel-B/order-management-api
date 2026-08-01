package com.cursobackend.aula6.application.order.dto;

import java.time.LocalDateTime;

public record ExceptionErrorResponse(String error, String message, LocalDateTime timesTamp) {

}
