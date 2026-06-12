package com.cursobackend.aula6.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursobackend.aula6.domain.orders.model.OrderStatus;
import com.cursobackend.aula6.domain.orders.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
	
	List<Orders> findByStatus(OrderStatus status);
	
}
