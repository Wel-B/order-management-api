package com.cursobackend.aula6.domain.order.policy;

public interface CreditPolicy {
	
	CreditDecision avaluate(int score);
	
}
