package com.cursobackend.aula6.infrastructure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cursobackend.aula6.application.user.usecase.DeleteUser;

@Component
public class UserScheduler {

	private final DeleteUser deleteUser;
	
	public UserScheduler(DeleteUser deleteUser) {
		this.deleteUser = deleteUser;
	}
	
	@Scheduled(cron = "0 0 2 * * *")
	public void execute() {
		
		deleteUser.execute();
	}
	
}
