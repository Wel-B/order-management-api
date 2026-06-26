package com.cursobackend.aula6.application.user.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.user.dto.UserAuthRequestDTO;
import com.cursobackend.aula6.domain.user.exception.InvalidCredentialsException;
import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.infrastructure.repository.UserRepository;

@Service
public class UserLogin {

	private UserRepository userRepository;
	private PasswordEncoder encoder;
	
	public UserLogin(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}
	
	public Users execute(UserAuthRequestDTO request) {
		
		Users users = userRepository.findByEmail(request.email())
				.orElseThrow(() -> new InvalidCredentialsException("Credencial inválida"));
		
		if(!encoder.matches(request.password(), users.getPassword())) {
			throw new InvalidCredentialsException("Credencial inválida");
		}
		
		return users;
	}
	
}
