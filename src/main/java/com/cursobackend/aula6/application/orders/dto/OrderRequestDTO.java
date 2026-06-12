package com.cursobackend.aula6.application.orders.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequestDTO(
		@NotNull(message = "Valor é obrigatório") 
		@Positive(message = "Valor deve ser maior que zero") Double amount) {

}
