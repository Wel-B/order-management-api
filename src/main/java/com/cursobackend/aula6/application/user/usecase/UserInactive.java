package com.cursobackend.aula6.application.user.usecase;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.cursobackend.aula6.application.user.dto.UserAuthRequestDTO;
import com.cursobackend.aula6.domain.orders.exception.ForbiddenActionException;
import com.cursobackend.aula6.domain.user.exception.InvalidCredentialsException;
import com.cursobackend.aula6.domain.user.exception.UserNotFoundException;
import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.domain.user.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserInactive {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	
	public UserInactive(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}
	
	public void execute(@Valid @RequestBody UserAuthRequestDTO request) {
		
		Users users = userRepository.findByEmail(request.email())
				.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
		
		if(!encoder.matches(request.password(), users.getPassword())) {
			throw new InvalidCredentialsException("Credencial inválida");
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
		
		boolean isOwner = users.getEmail().equals(email);
		
		if (!isOwner) {
			throw new ForbiddenActionException("Você não pode eliminar essa conta");
		}
		
		users.inactiveUser();
		
		userRepository.save(users);
	}
	
}
