package com.cursobackend.aula6.application.orders.dto;

import java.time.LocalDateTime;

public record OrderResponseDTO(Long id, Double amount, String status, LocalDateTime creationDate) {

}
