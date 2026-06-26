package com.cursobackend.aula6.domain.user.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
	
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime creationDate;
	
	public Users() {
		this.status = UserStatus.ACTIVE;
	}
	
	public Long getId() {return id;}
	
	public String getEmail() {return email;}
	
	public String getPassword() {return password;}
	
	public Role getRole() {return role;}
	
	public UserStatus getStatus() {return status;}
	
	public LocalDateTime getCreationDate() {return creationDate;}
	
	
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
	
	public void inactiveUser() {
		
		if (status != UserStatus.ACTIVE) {
			throw new IllegalStateException("Estado inválido");
		}
		
		status = UserStatus.INACTIVE;
	}
	
	public void deleteUser() {
		
		if (status != UserStatus.INACTIVE) {
			throw new IllegalStateException("Estado inválido");
		}
		
		status = UserStatus.DELETED;
	}
	
}












