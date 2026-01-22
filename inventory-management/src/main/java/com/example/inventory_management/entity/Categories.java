package com.example.inventory_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")

public class Categories {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categories_id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private boolean status;

	public Categories() {

	}

	public Long getCategories_id() {
		return categories_id;
	}

	public void setCategories_id(Long categories_id) {
		this.categories_id = categories_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
