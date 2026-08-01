package com.cursobackend.aula6.application.user.usecase;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DeleteUser {

	private final UserRepository userRepository;
	
	public DeleteUser(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void execute() {
		
		LocalDateTime limitDate = LocalDateTime.now().minusDays(90);
		
		List<Users> users = userRepository.findInactiveUsers(limitDate);
		
		for (Users toUsers : users) {
			
			toUsers.deleteUser();
		
			userRepository.save(toUsers);
			
		}
		
	}
	
}
