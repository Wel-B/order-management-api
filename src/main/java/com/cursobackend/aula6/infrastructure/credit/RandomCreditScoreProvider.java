package com.cursobackend.aula6.infrastructure.credit;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.service.CreditScoreProvider;

@Service
public class RandomCreditScoreProvider implements CreditScoreProvider {

	private final Random random = new Random();
	
	@Override
	public int getScore() {
		
		return random.nextInt(600, 900);
	}

}
