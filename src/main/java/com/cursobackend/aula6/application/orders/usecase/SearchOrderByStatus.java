package com.cursobackend.aula6.application.orders.usecase;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.orders.dto.OrderResponseDTO;
import com.cursobackend.aula6.application.orders.mapper.OrderMapper;
import com.cursobackend.aula6.domain.orders.model.OrderStatus;
import com.cursobackend.aula6.infrastructure.repository.OrderRepository;

@Service
public class SearchOrderByStatus {

	private OrderRepository repository;
	private OrderMapper mapper;
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(SearchOrderByStatus.class);
	
	public SearchOrderByStatus(OrderRepository repository, OrderMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public List<OrderResponseDTO> execute(OrderStatus status) {
		
		log.info("Buscando status | status = {} |", status);
		
		return repository.findByStatus(status)
				.stream()
				.map(mapper::toResponseDTO)
				.toList();
	}
	
}
