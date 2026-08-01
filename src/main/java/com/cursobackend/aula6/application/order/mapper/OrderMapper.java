package com.cursobackend.aula6.application.order.mapper;

import org.springframework.stereotype.Component;

import com.cursobackend.aula6.application.order.dto.OrderResponseDTO;
import com.cursobackend.aula6.domain.order.model.Orders;

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
