package com.cursobackend.aula6.application.orders.usecase;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.orders.dto.OrderResponseDTO;
import com.cursobackend.aula6.application.orders.mapper.OrderMapper;
import com.cursobackend.aula6.application.service.CreditScoreProvider;
import com.cursobackend.aula6.domain.orders.exception.OrderNotFoundException;
import com.cursobackend.aula6.domain.orders.model.Orders;
import com.cursobackend.aula6.domain.orders.policy.CreditDecision;
import com.cursobackend.aula6.domain.orders.policy.CreditPolicy;
import com.cursobackend.aula6.infrastructure.repository.OrderRepository;

@Service
public class AnalyzeOrder {

	private OrderRepository repository;
	private CreditPolicy policy;
	private OrderMapper mapper;
	private CreditScoreProvider creditScoreProvider;
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(AnalyzeOrder.class);
	
	public AnalyzeOrder(
			OrderRepository repository,
			CreditPolicy policy,
			OrderMapper mapper,
			CreditScoreProvider creditScoreProvider) {
		
		this.repository = repository;
		this.policy = policy;
		this.mapper = mapper;
		this.creditScoreProvider = creditScoreProvider;
	}
	
	public OrderResponseDTO execute(Long id) {
		
		log.info("Analizando pedido | ID = {} |", id);
		
		Orders orders = repository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));
		
		orders.putInAnalyze();
		
		int score = creditScoreProvider.getScore();
		
		log.info("Score obtido | score = {} |", score);
		
		CreditDecision decision = policy.avaluate(score);
		
		switch (decision) {
			case APPROVE -> orders.approve();
			case REJECT -> orders.reject();
			case MANUAL_ANALYZE -> orders.manualAnalyze();
		}
		
		log.info("Decisão tomada | status = {} |", orders.getStatus());
		
		repository.save(orders);
		
		return mapper.toResponseDTO(orders);
	}
	
}
