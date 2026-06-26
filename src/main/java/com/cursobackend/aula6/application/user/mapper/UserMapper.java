package com.cursobackend.aula6.application.user.mapper;

import org.springframework.stereotype.Component;

import com.cursobackend.aula6.application.user.dto.UserAuthResponseDTO;
import com.cursobackend.aula6.domain.user.model.Users;

@Component
public class UserMapper {

	public UserAuthResponseDTO toResponseDTO(Users users) {
		
		return new UserAuthResponseDTO(
				users.getId(),
				users.getStatus().name(),
				users.getCreationDate()
		);
		
	}
	
}
