package com.cursobackend.aula6.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.domain.user.repository.UserRepository;

public interface JpaUserRepository extends JpaRepository<Users, Long>, UserRepository {

	@Query("""
			SELECT u
			FROM Users u
			WHERE u.status = 'INACTIVE'
			AND u.statusChangedAt <= :limitDate
		""")
	
	List<Users> findInactiveUsers(LocalDateTime limitDate);
	
}
