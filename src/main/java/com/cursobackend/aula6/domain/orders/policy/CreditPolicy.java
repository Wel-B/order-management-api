package com.cursobackend.aula6.domain.orders.policy;

public interface CreditPolicy {
	
	CreditDecision avaluate(int score);
	
}
