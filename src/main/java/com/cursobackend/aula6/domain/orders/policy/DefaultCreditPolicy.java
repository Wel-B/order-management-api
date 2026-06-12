package com.cursobackend.aula6.domain.orders.policy;

import org.springframework.stereotype.Service;

@Service
public class DefaultCreditPolicy implements CreditPolicy {

	@Override
	public CreditDecision avaluate(int score) {
		if (score > 800) {
			return CreditDecision.APPROVE;
		}
		if (score < 700) {
			return CreditDecision.REJECT;
		}
		return CreditDecision.MANUAL_ANALYZE;
	}
	
}
