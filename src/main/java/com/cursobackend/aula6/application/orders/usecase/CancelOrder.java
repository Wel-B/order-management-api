package com.cursobackend.aula6.application.orders.usecase;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.application.orders.dto.OrderResponseDTO;
import com.cursobackend.aula6.application.orders.mapper.OrderMapper;
import com.cursobackend.aula6.domain.orders.exception.ForbiddenActionException;
import com.cursobackend.aula6.domain.orders.exception.OrderNotFoundException;
import com.cursobackend.aula6.domain.orders.model.Orders;
import com.cursobackend.aula6.domain.user.exception.UserNotFoundException;
import com.cursobackend.aula6.domain.user.model.Role;
import com.cursobackend.aula6.domain.user.model.Users;
import com.cursobackend.aula6.infrastructure.repository.OrderRepository;
import com.cursobackend.aula6.infrastructure.repository.UserRepository;

@Service
public class CancelOrder {

	private OrderRepository orderRepository;
	private UserRepository userRepository;
	private OrderMapper mapper;
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(CancelOrder.class);
	
	public CancelOrder(OrderRepository orderRepository, UserRepository userRepository, OrderMapper mapper) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.mapper = mapper;
	}
	
	public OrderResponseDTO execute(Long id) {
		
		Orders orders = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
		
		Users currentUser = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
		
		boolean isOwner = orders.getUsers().getEmail().equals(email);
		
		boolean isAdmin = currentUser.getRole() == Role.ADMIN;
		
		if (!isOwner && !isAdmin) {
			throw new ForbiddenActionException("Você não pode cancelar este pedido");
		}
		
		log.info("Cancelling the order | ID = {} |", id);
		
		orders.cancel();
		
		orderRepository.save(orders);
		
		log.info("Order canceled | status = {} |", orders.getStatus());
		
		return mapper.toResponseDTO(orders);
	}
	
}
