package com.cursobackend.aula6.application.orders.dto;

import java.time.LocalDateTime;

public record ExceptionErrorResponse(String error, String message, LocalDateTime timesTamp) {

}
