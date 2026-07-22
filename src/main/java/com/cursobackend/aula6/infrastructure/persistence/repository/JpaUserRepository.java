package com.cursobackend.aula6.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.domain.user.repository.UserRepository;

public interface JpaUserRepository extends JpaRepository<Users, Long>, UserRepository {

}
