package com.cursobackend.aula6.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setEmail(String email) {
		
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("Username inválido");
		}
		
		this.email = email;
	}
	
	public void setPassword(String password) {
		
		if (password ==null || password.isBlank()) {
			throw new IllegalArgumentException("Password inválido");
		}
		
		this.password = password;
	}
	
	public void setRole(Role role) {
		
		if (role != Role.USER) {
			throw new IllegalArgumentException("Role inválido");
		}
		
		this.role = role;
	}
	
}
