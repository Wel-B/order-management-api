package com.cursobackend.aula6.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursobackend.aula6.domain.user.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	
	Optional<Users> findByEmail(String email);
	
}
