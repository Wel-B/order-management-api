package com.cursobackend.aula6.orders.unit.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cursobackend.aula6.application.orders.dto.OrderResponseDTO;
import com.cursobackend.aula6.application.orders.mapper.OrderMapper;
import com.cursobackend.aula6.application.orders.usecase.AnalyzeOrder;
import com.cursobackend.aula6.application.service.CreditScoreProvider;
import com.cursobackend.aula6.domain.orders.model.Orders;
import com.cursobackend.aula6.domain.orders.policy.CreditDecision;
import com.cursobackend.aula6.domain.orders.policy.CreditPolicy;
import com.cursobackend.aula6.domain.orders.repository.OrderRepository;
import com.cursobackend.aula6.domain.user.model.Users;

public class AnalyzeOrderTest {
	
	private AnalyzeOrder analyzeOrder;
	private OrderRepository orderRepository;
	private CreditScoreProvider creditScoreProvider;
	private CreditPolicy policy;
	private OrderMapper mapper;
	
	@BeforeEach
	void setup() {
		
		orderRepository = mock(OrderRepository.class);
		creditScoreProvider = mock(CreditScoreProvider.class);
		policy = mock(CreditPolicy.class);
		
		mapper = new OrderMapper();
		
		analyzeOrder = new AnalyzeOrder(orderRepository, policy, mapper, creditScoreProvider);	
	}
	
	@Test
	void ShouldApproveOrderWhenScoreAbove800() {
		
		Users users = new Users("user@email.com", "123456");
		
		Orders orders = new Orders(1500.0, users);
		
		when(orderRepository.findById(1L)).thenReturn(Optional.of(orders));
		
		when(creditScoreProvider.getScore()).thenReturn(850);
		
		when(policy.avaluate(850)).thenReturn(CreditDecision.APPROVE);
		
		OrderResponseDTO result = analyzeOrder.execute(1L);
		
		assertEquals("APPROVED", result.status());
		
		verify(orderRepository).save(orders);
		
	}
	
}
