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
import com.cursobackend.aula6.infrastructure.repository.OrderRepository;

public class AnalyzeOrderTest {
	
	private AnalyzeOrder analyzeOrder;
	private OrderRepository repository;
	private CreditScoreProvider creditScoreProvider;
	private CreditPolicy policy;
	private OrderMapper mapper;
	
	@BeforeEach
	void setup() {
		
		repository = mock(OrderRepository.class);
		creditScoreProvider = mock(CreditScoreProvider.class);
		policy = mock(CreditPolicy.class);
		
		mapper = new OrderMapper();
		
		analyzeOrder = new AnalyzeOrder(repository, policy, mapper, creditScoreProvider);	
	}
	
	@Test
	void ShouldApproveOrderWhenScoreAbove800() {
		
		Orders orders = new Orders();
		
		when(repository.findById(1L)).thenReturn(Optional.of(orders));
		
		when(creditScoreProvider.getScore()).thenReturn(850);
		
		when(policy.avaluate(850)).thenReturn(CreditDecision.APPROVE);
		
		OrderResponseDTO result = analyzeOrder.execute(1L);
		
		assertEquals("APPROVED", result.status());
		
		verify(repository).save(orders);
		
	}
	
}



