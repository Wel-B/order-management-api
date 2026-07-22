package com.cursobackend.aula6.application.orders.usecase;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.orders.dto.OrderResponseDTO;
import com.cursobackend.aula6.application.orders.mapper.OrderMapper;
import com.cursobackend.aula6.domain.orders.model.OrderStatus;
import com.cursobackend.aula6.domain.orders.repository.OrderRepository;
import com.cursobackend.aula6.domain.user.exception.UserNotFoundException;
import com.cursobackend.aula6.domain.user.repository.UserRepository;

@Service
public class SearchOrderByStatus {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final OrderMapper mapper;
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(SearchOrderByStatus.class);
	
	public SearchOrderByStatus(OrderRepository orderRepository, UserRepository userRepository, OrderMapper mapper) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.mapper = mapper;
	}
	
	public List<OrderResponseDTO> execute(OrderStatus status) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
		
		userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
		
		log.info("Getting status | status = {} |", status);
		
		return orderRepository.findByStatus(status)
				.stream()
				.map(mapper::toResponseDTO)
				.toList();
	}
	
}
