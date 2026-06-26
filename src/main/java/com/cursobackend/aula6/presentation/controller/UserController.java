package com.cursobackend.aula6.presentation.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursobackend.aula6.application.user.dto.UserAuthRequestDTO;
import com.cursobackend.aula6.application.user.usecase.UserInactive;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserInactive userInactive;
	
	public UserController(UserInactive userInactive) {
		this.userInactive = userInactive;
	}
	
	@PatchMapping("/inactive")
	public void delete(@Valid @RequestBody UserAuthRequestDTO request) {
		
		userInactive.execute(request);
	}
	
}
