package com.cursobackend.aula6.presentation.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursobackend.aula6.application.user.dto.UserAuthRequestDTO;
import com.cursobackend.aula6.application.user.usecase.UserLogin;
import com.cursobackend.aula6.application.user.usecase.UserRegister;
import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.infrastructure.security.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserRegister register;
	private UserLogin login;
	private JwtService jwtService;
	
	public AuthController(UserRegister register, UserLogin login, JwtService jwtService) {
		this.jwtService = jwtService;
		this.login = login;
		this.register = register;
	}
	
	@PostMapping("/register")
	public void register(@Valid @RequestBody UserAuthRequestDTO request) {
		register.execute(request);
	}
	
	@PostMapping("/login")
	public Map<String, String> login(@Valid @RequestBody UserAuthRequestDTO request) {
		
		Users users = login.execute(request);
		
		String token = jwtService.generateToken(
				users.getEmail(),
				users.getRole()
		);
		
		return Map.of("token", token);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		
		return ResponseEntity.ok("Logout realizado com sucesso");
		//Descartamos o token no Frontend
	}
	
}
