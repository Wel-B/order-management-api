package com.cursobackend.aula6.application.user.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.user.dto.UserAuthRequestDTO;
import com.cursobackend.aula6.domain.user.exception.DuplicateEmailException;
import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.domain.user.repository.UserRepository;

@Service
public class UserRegister {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	
	public UserRegister(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}
	
	public void execute(UserAuthRequestDTO request) {
		
		if (userRepository.findByEmail(request.email()).isPresent()) {
			throw new DuplicateEmailException("Email já cadastrado");
		}
		
		Users users = new Users(request.email(), encoder.encode(request.password()));
		
		userRepository.save(users);	
	}
	
}
