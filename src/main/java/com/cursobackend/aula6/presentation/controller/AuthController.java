package com.cursobackend.aula6.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cursobackend.aula6.application.user.dto.UserAuthRequestDTO;
import com.cursobackend.aula6.application.user.dto.UserAuthResponseDTO;
import com.cursobackend.aula6.application.user.usecase.LoginUser;
import com.cursobackend.aula6.application.user.usecase.RegisterUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final RegisterUser registerUser;
	private final LoginUser loginUser;
	
	public AuthController(RegisterUser registerUser, LoginUser loginUser) {
		this.loginUser = loginUser;
		this.registerUser = registerUser;
	}
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@Valid @RequestBody UserAuthRequestDTO request) {
		
		registerUser.execute(request);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserAuthResponseDTO> login(@Valid @RequestBody UserAuthRequestDTO request) {
		
		return ResponseEntity.ok(loginUser.execute(request));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		
		return ResponseEntity.ok("Logout realizado com sucesso");
		
		// We discard the token on the frontend
	}
	
}
