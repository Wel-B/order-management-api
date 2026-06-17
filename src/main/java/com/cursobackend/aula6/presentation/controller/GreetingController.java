package com.cursobackend.aula6.presentation.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class GreetingController {

	@GetMapping("/")
	public String greeting() {
		return "Bem-vindo ao backend";
	}
	
}
