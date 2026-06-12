package com.cursobackend.aula6.presentation.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class GreetingController {

	@GetMapping("/")
	public String greeting() {
		return "Bem-vindo ao backend";
	}
	
}
