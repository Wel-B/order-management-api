package com.cursobackend.aula6.application.user.dto;

import java.time.LocalDateTime;

public record UserAuthResponseDTO(Long id, String Status, LocalDateTime creationDate) {
	
}
