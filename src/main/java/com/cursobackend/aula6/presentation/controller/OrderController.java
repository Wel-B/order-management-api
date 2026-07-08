package com.cursobackend.aula6.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cursobackend.aula6.application.orders.dto.OrderRequestDTO;
import com.cursobackend.aula6.application.orders.dto.OrderResponseDTO;
import com.cursobackend.aula6.application.orders.usecase.AnalyzeOrder;
import com.cursobackend.aula6.application.orders.usecase.CancelOrder;
import com.cursobackend.aula6.application.orders.usecase.CreateOrder;
import com.cursobackend.aula6.application.orders.usecase.SearchOrderByStatus;
import com.cursobackend.aula6.domain.orders.model.OrderStatus;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/orders")
public class OrderController {

	private CreateOrder createOrder;
	private AnalyzeOrder analyzeOrder;
	private CancelOrder cancelOrder;
	private SearchOrderByStatus searchByStatus;
	
	public OrderController(CreateOrder createOrder, AnalyzeOrder analyzeOrder, CancelOrder cancelOrder,
			SearchOrderByStatus searchByStatus) {
		
		this.createOrder = createOrder;
		this.analyzeOrder = analyzeOrder;
		this.cancelOrder = cancelOrder;
		this.searchByStatus = searchByStatus;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderRequestDTO dto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createOrder.execute(dto));
	}
	
	@PostMapping("/{id}/analyze")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OrderResponseDTO> analyze(@PathVariable Long id) {
		
		return ResponseEntity.ok(analyzeOrder.execute(id));
	}
	
	@PatchMapping("/{id}/cancel")
	public ResponseEntity<OrderResponseDTO> cancel(@PathVariable Long id) {
		
		return ResponseEntity.ok(cancelOrder.execute(id));
	}
	
	@GetMapping("/status")
	public ResponseEntity<List<OrderResponseDTO>> searchByStatus(@RequestParam OrderStatus status) {
		
		return ResponseEntity.ok(searchByStatus.execute(status));
	}
	
}
