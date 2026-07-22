package com.cursobackend.aula6.application.user.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.user.dto.UserAuthRequestDTO;
import com.cursobackend.aula6.application.user.dto.UserAuthResponseDTO;
import com.cursobackend.aula6.application.user.mapper.UserMapper;
import com.cursobackend.aula6.domain.user.exception.InvalidCredentialsException;
import com.cursobackend.aula6.domain.user.exception.UserNotFoundException;
import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.domain.user.repository.UserRepository;
import com.cursobackend.aula6.infrastructure.security.JwtService;

@Service
public class UserLogin {

	private final UserRepository userRepository;
	private final UserMapper mapper;
	private final PasswordEncoder encoder;
	private final JwtService jwtService;
	
	public UserLogin(UserRepository userRepository, UserMapper mapper, PasswordEncoder encoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.mapper = mapper;
		this.encoder = encoder;
		this.jwtService = jwtService;
	}
	
	public UserAuthResponseDTO execute(UserAuthRequestDTO request) {
		
		Users users = userRepository.findByEmail(request.email())
				.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
		
		if(!encoder.matches(request.password(), users.getPassword())) {
			throw new InvalidCredentialsException("Credencial inválida");
		}
		
		String token = jwtService.generateToken(
				users.getEmail(),
				users.getRole()
		);
		
		return mapper.toResponseDTO(token);
	}
	
}
