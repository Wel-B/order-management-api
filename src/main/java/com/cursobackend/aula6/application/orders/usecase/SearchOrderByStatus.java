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

	private OrderRepository orderRepository;
	private OrderMapper mapper;
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(SearchOrderByStatus.class);
	
	public SearchOrderByStatus(OrderRepository orderRepository, OrderMapper mapper) {
		this.orderRepository = orderRepository;
		this.mapper = mapper;
	}
	
	public List<OrderResponseDTO> execute(OrderStatus status) {
		
		log.info("Getting status | status = {} |", status);
		
		return orderRepository.findByStatus(status)
				.stream()
				.map(mapper::toResponseDTO)
				.toList();
	}
	
}
