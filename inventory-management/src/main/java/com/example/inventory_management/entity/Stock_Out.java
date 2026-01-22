package com.example.inventory_management.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock_out")
public class Stock_Out {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnoreProperties({ "categories_id", "price", "current_stock", "reorder_stock", "is_active", "created_at" })
	private Products product_id;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private String issued_to;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private LogIn issued_by;

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(nullable = false)
	private LocalDate issued_date;

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getIssued_to() {
		return issued_to;
	}

	public void setIssued_to(String issued_to) {
		this.issued_to = issued_to;
	}

	public LogIn getIssued_by() {
		return issued_by;
	}

	public void setIssued_by(LogIn issued_by) {
		this.issued_by = issued_by;
	}

	public LocalDate getIssued_date() {
		return issued_date;
	}

	public void setIssued_date(LocalDate issued_date) {
		this.issued_date = issued_date;
	}

}
