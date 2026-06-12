package com.cursobackend.aula6.application.orders.usecase;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.orders.dto.OrderRequestDTO;
import com.cursobackend.aula6.application.orders.dto.OrderResponseDTO;
import com.cursobackend.aula6.application.orders.mapper.OrderMapper;
import com.cursobackend.aula6.domain.orders.model.Orders;
import com.cursobackend.aula6.domain.user.exception.UserNotFoundException;
import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.infrastructure.repository.OrderRepository;
import com.cursobackend.aula6.infrastructure.repository.UserRepository;

@Service
public class CreateOrder {

	private OrderRepository repository;
	private UserRepository userRepository;
	private OrderMapper mapper;
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(CreateOrder.class);
	
	public CreateOrder(OrderRepository repository, UserRepository userRepository, OrderMapper mapper) {
		this.repository = repository;
		this.userRepository = userRepository;
		this.mapper = mapper;
	}
	
	public OrderResponseDTO execute(OrderRequestDTO dto) {
		
		log.info("Criando pedido | amount = {} |", dto.amount());
		
		Orders orders = new Orders();
		orders.setAmount(dto.amount());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
		
		Users users = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
		
		orders.setUser(users);
		
		repository.save(orders);
		
		log.info("Pedido Criado com sucesso | ID = {} | status = {} |", orders.getId(), orders.getStatus());
		
		return mapper.toResponseDTO(orders);
	}
	
}
