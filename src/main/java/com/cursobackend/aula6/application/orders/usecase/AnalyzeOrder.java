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
import com.cursobackend.aula6.domain.orders.repository.OrderRepository;

@Service
public class AnalyzeOrder {

	private final OrderRepository orderRepository;
	private final CreditPolicy policy;
	private final OrderMapper mapper;
	private final CreditScoreProvider creditScoreProvider;
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(AnalyzeOrder.class);
	
	public AnalyzeOrder(
			OrderRepository orderRepository,
			CreditPolicy policy,
			OrderMapper mapper,
			CreditScoreProvider creditScoreProvider) {
		
		this.orderRepository = orderRepository;
		this.policy = policy;
		this.mapper = mapper;
		this.creditScoreProvider = creditScoreProvider;
	}
	
	public OrderResponseDTO execute(Long id) {
		
		log.info("Analyzing the order | ID = {} |", id);
		
		Orders orders = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));
		
		orders.putInAnalyze();
		
		int score = creditScoreProvider.getScore();
		
		log.info("Calculating score | score = {} |", score);
		
		CreditDecision decision = policy.avaluate(score);
		
		switch (decision) {
			case APPROVE -> orders.approve();
			case REJECT -> orders.reject();
			case MANUAL_ANALYZE -> orders.manualAnalyze();
		}
		
		log.info("Decision made | status = {} |", orders.getStatus());
		
		orderRepository.save(orders);
		
		return mapper.toResponseDTO(orders);
	}
	
}
