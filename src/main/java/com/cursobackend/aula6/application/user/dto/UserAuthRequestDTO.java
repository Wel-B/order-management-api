package com.cursobackend.aula6.application.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserAuthRequestDTO (
		@Email(message = "Email inválido")
		@NotBlank(message = "Email não pode estar vazio") 
		String email,
		
		@NotBlank(message = "Password não pode estar vazio")
		@Size(min = 6, message = "Password deve ter no mínimo 6 caracteres")
		String password) {

}
