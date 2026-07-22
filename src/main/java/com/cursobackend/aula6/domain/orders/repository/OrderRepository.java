package com.cursobackend.aula6.domain.orders.repository;

import java.util.List;
import java.util.Optional;

import com.cursobackend.aula6.domain.orders.model.OrderStatus;
import com.cursobackend.aula6.domain.orders.model.Orders;

public interface OrderRepository {

	Orders save(Orders orders);
	
	Optional<Orders> findById(Long id);
	
	List<Orders> findByStatus(OrderStatus status);
	
	List<Orders> findAll();
	
}
