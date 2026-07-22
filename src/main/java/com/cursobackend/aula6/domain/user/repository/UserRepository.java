package com.cursobackend.aula6.domain.user.repository;

import java.util.List;
import java.util.Optional;

import com.cursobackend.aula6.domain.user.model.UserStatus;
import com.cursobackend.aula6.domain.user.model.Users;

public interface UserRepository {

	Users save(Users users);
	
	Optional<Users> findById(Long id);
	
	Optional<Users> findByEmail(String email);
	
	List<Users> findByStatus(UserStatus status);
	
	List<Users> findAll();
	
}
