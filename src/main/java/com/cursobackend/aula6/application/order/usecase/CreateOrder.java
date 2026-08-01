package com.cursobackend.aula6.application.order.usecase;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.order.dto.OrderRequestDTO;
import com.cursobackend.aula6.application.order.dto.OrderResponseDTO;
import com.cursobackend.aula6.application.order.mapper.OrderMapper;
import com.cursobackend.aula6.domain.order.exception.InvalidStateException;
import com.cursobackend.aula6.domain.order.model.Orders;
import com.cursobackend.aula6.domain.order.repository.OrderRepository;
import com.cursobackend.aula6.domain.user.exception.UserNotFoundException;
import com.cursobackend.aula6.domain.user.model.UserStatus;
import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.domain.user.repository.UserRepository;

@Service
public class CreateOrder {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final OrderMapper mapper;
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(CreateOrder.class);
	
	public CreateOrder(OrderRepository orderRepository, UserRepository userRepository, OrderMapper mapper) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.mapper = mapper;
	}
	
	public OrderResponseDTO execute(OrderRequestDTO dto) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
		
		Users users = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
		
		if (users.getStatus() != UserStatus.ACTIVE) {
			throw new InvalidStateException("Conta inactiva");
		}

		log.info("Creating the order | amount = {} |", dto.amount());
		
		Orders orders = new Orders(dto.amount(), users);
		
		orderRepository.save(orders);
		
		log.info("Order created successfully | ID = {} | status = {} |", orders.getId(), orders.getStatus());
		
		return mapper.toResponseDTO(orders);
	}
	
}
