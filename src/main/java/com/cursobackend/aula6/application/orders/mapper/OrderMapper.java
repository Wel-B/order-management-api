package com.cursobackend.aula6.application.orders.mapper;

import org.springframework.stereotype.Component;

import com.cursobackend.aula6.application.orders.dto.OrderResponseDTO;
import com.cursobackend.aula6.domain.orders.model.Orders;

@Component
public class OrderMapper {
	
	public OrderResponseDTO toResponseDTO(Orders orders) {
		
		return new OrderResponseDTO(
				orders.getId(),
				orders.getAmount(),
				orders.getStatus().name(),
				orders.getCreationDate()
		);
		
	}
	
}
