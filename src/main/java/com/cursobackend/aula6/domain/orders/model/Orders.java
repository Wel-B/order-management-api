package com.cursobackend.aula6.domain.orders.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.cursobackend.aula6.domain.orders.exception.InvalidStateException;
import com.cursobackend.aula6.domain.user.model.UserStatus;
import com.cursobackend.aula6.domain.user.model.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double amount;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users users;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime creationDate;
	
	public Orders() {
		this.status = OrderStatus.REQUESTED;
	}
	
	public Long getId() {return id;}
	
	public Double getAmount() {return amount;}
	
	public Users getUsers() {return users;}
	
	public OrderStatus getStatus() {return status;}
	
	public LocalDateTime getCreationDate() {return creationDate;}
	
	public void putInAnalyze() {
		
		if (status != OrderStatus.REQUESTED) {
			throw new InvalidStateException("Pedido não pode entrar em análise");
		}
		status = OrderStatus.ANALYZING;
	}
	
	public void approve() {
		
		if (status != OrderStatus.ANALYZING) {
			throw new InvalidStateException("Pedido não pode ser aprovado");
		}
		status = OrderStatus.APPROVED;
	}
	
	public void reject() {
		
		if (status != OrderStatus.ANALYZING) {
			throw new InvalidStateException("Pedido não pode ser rejeitado");
		}
		status = OrderStatus.REJECTED;
	}
	
	public void manualAnalyze() {
		
		if (status != OrderStatus.ANALYZING) {
			throw new InvalidStateException("Pedido não pode entrar em análise manual");
		}
		status = OrderStatus.MANUAL_ANALYZE;
	}
	
	public void cancel() {
		
		if (users.getStatus() != UserStatus.ACTIVE) {
			throw new InvalidStateException("Conta inactiva");
		}
		
		if (status != OrderStatus.REQUESTED) {
			throw new InvalidStateException("Pedido não pode ser cancelado");
		}
		status = OrderStatus.CANCELED;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setUser(Users users) {
		this.users = users;
	}
	
}
