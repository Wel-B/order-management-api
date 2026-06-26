package com.cursobackend.aula6.application.user.usecase;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.user.dto.UserAuthRequestDTO;
import com.cursobackend.aula6.domain.orders.exception.ForbiddenActionException;
import com.cursobackend.aula6.domain.user.exception.InvalidCredentialsException;
import com.cursobackend.aula6.domain.user.exception.UserNotFoundException;
import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.infrastructure.repository.UserRepository;

@Service
public class UserDelete {

	private UserRepository userRepository;
	private PasswordEncoder encoder;
	
	public UserDelete(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}
	
	public void delete(UserAuthRequestDTO request) {
		
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
		
		userRepository.delete(users);
	}
	
}
