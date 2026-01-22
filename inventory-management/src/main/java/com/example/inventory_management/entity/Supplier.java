package com.example.inventory_management.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="supplier")
public class Supplier {
	
	public Supplier(Long supplier_id, String supplier_name, String email, Boolean status) {
		super();
		this.supplier_id = supplier_id;
		this.supplier_name = supplier_name;
		this.email = email;
		this.status = status;
	}

	public Supplier() {
		
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("supplier_id")
	private Long supplier_id;
	

	@Column(nullable=false)
	private String supplier_name;
	
	@Column(nullable=false,unique=true)
	private String email;
	
	@Column(nullable=false)
	private Boolean status;
	
	public Long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
