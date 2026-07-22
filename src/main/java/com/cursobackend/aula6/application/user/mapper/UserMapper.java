package com.cursobackend.aula6.application.user.mapper;

import org.springframework.stereotype.Component;

import com.cursobackend.aula6.application.user.dto.UserAuthResponseDTO;

@Component
public class UserMapper {

	public UserAuthResponseDTO toResponseDTO(String token) {
		
		return new UserAuthResponseDTO(
				token
		);
		
	}
	
}
