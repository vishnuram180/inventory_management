package com.example.inventory_management.entity;

import java.time.OffsetDateTime;

import com.example.inventory_management.Enum.actions;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "stock_logs")
public class Stock_Logs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnoreProperties({ "categories_id", "price", "current_stock", "reorder_stock", "is_active", "created_at" })
	private Products product_id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private actions action;

	@Column(nullable = false)
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties({ "name", "password", "role", "status", "created_at" })
	private LogIn action_by;

	@Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime action_date;

	// getters and setter

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Products getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Products product_id) {
		this.product_id = product_id;
	}

	public actions getAction() {
		return action;
	}

	public void setAction(actions action) {
		this.action = action;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LogIn getAction_by() {
		return action_by;
	}

	public void setAction_by(LogIn action_by) {
		this.action_by = action_by;
	}

	public OffsetDateTime getAction_date() {
		return action_date;
	}

	public void setAction_date(OffsetDateTime action_date) {
		this.action_date = action_date;
	}
}
