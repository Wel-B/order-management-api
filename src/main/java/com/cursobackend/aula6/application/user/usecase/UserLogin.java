package com.cursobackend.aula6.application.user.usecase;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.user.dto.UserAuthRequestDTO;
import com.cursobackend.aula6.domain.user.exception.InvalidCredentialsException;
import com.cursobackend.aula6.domain.user.exception.UserNotFoundException;
import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.infrastructure.repository.UserRepository;
import com.cursobackend.aula6.infrastructure.security.JwtService;

@Service
public class UserLogin {

	private UserRepository userRepository;
	private PasswordEncoder encoder;
	private JwtService jwtService;
	
	public UserLogin(UserRepository userRepository, PasswordEncoder encoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.jwtService = jwtService;
	}
	
	public Map<String, String> execute(UserAuthRequestDTO request) {
		
		Users users = userRepository.findByEmail(request.email())
				.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
		
		if(!encoder.matches(request.password(), users.getPassword())) {
			throw new InvalidCredentialsException("Credencial inválida");
		}
		
		String token = jwtService.generateToken(
				users.getEmail(),
				users.getRole()
		);
		
		return Map.of("Token", token);
	}
	
}
