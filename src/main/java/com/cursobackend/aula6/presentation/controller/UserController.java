package com.cursobackend.aula6.presentation.controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursobackend.aula6.application.user.dto.UserAuthRequestDTO;
import com.cursobackend.aula6.application.user.usecase.ActivateUser;
import com.cursobackend.aula6.application.user.usecase.DeactivateUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final DeactivateUser deactivateUser;
	private final ActivateUser activateUser;
	
	public UserController(DeactivateUser deactivateUser, ActivateUser activateUser) {
		this.deactivateUser = deactivateUser;
		this.activateUser = activateUser;
	}
	
	@PatchMapping("/deactivate")
	public void delete(@Valid @RequestBody UserAuthRequestDTO request) {
		
		deactivateUser.execute(request);
	}
	
	@PatchMapping("/activate")
	public void activate(@Valid @RequestBody UserAuthRequestDTO request) {
		
		activateUser.execute(request);
	}
	
}
