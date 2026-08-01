package com.cursobackend.aula6.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursobackend.aula6.domain.order.model.Orders;
import com.cursobackend.aula6.domain.order.repository.OrderRepository;

public interface JpaOrderRepository extends JpaRepository<Orders, Long>, OrderRepository {

}
