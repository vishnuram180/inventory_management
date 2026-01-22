package com.example.inventory_management.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="stock_in")
public class Stock_In {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Products product_id;
	
	@ManyToOne
	@JoinColumn(name="supplier_id")
    private Supplier supplier_id;
	
	@Column(nullable=false)
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private LogIn recieved_by;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(nullable=false)
	private LocalDate recieved_date;
	
	public Stock_In() {
		
	}
	
	
	
	//getter & setter
	
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

	public Supplier getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Supplier supplier_id) {
		this.supplier_id = supplier_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LogIn getRecieved_by() {
		return recieved_by;
	}

	public void setRecieved_by(LogIn recieved_by) {
		this.recieved_by = recieved_by;
	}

	public LocalDate getRecieved_date() {
		return recieved_date;
	}

	public void setRecieved_date(LocalDate recieved_date) {
		this.recieved_date = recieved_date;
	}

	


	
}
