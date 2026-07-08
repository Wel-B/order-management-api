package com.cursobackend.aula6.presentation.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cursobackend.aula6.application.user.dto.UserAuthRequestDTO;
import com.cursobackend.aula6.application.user.usecase.UserLogin;
import com.cursobackend.aula6.application.user.usecase.UserRegister;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserRegister register;
	private UserLogin login;
	
	public AuthController(UserRegister register, UserLogin login) {
		this.login = login;
		this.register = register;
	}
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@Valid @RequestBody UserAuthRequestDTO request) {
		
		register.execute(request);
	}
	
	@PostMapping("/login")
	public Map<String, String> login(@Valid @RequestBody UserAuthRequestDTO request) {
		
		return login.execute(request);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		
		return ResponseEntity.ok("Logout realizado com sucesso");
		
		// We discard the token on the frontend
	}
	
}
