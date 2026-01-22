package com.example.inventory_management.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="products")
public class Products {
	
    public Products() {
    	super();
    }
	public Products(long product_id, Categories categories_id, String name, BigDecimal price, Integer current_stock,
			Integer reorder_stock, Boolean is_active, OffsetDateTime created_at) {
	
		this.product_id = product_id;
		this.categories_id = categories_id;
		this.name = name;
		this.price = price;
		this.current_stock = current_stock;
		this.reorder_stock = reorder_stock;
		this.is_active = is_active;
		this.created_at = created_at;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("product_id") // Add this!
	private Long product_id;	

	@ManyToOne
	@JoinColumn(name="categories_id")
    private Categories categories_id;
	

	@Column(nullable=false)
	private String name;
	
	@Column(precision=10,scale=2)
	private BigDecimal price;
	
	
	@Column(nullable=false)
	private Integer current_stock;
	
	@Column(nullable=false)
	private Integer reorder_stock;
	
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean is_active;
	
	@Column(columnDefinition="TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime created_at;
	
	
	
	
	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public Categories getCategories_id() {
		return categories_id;
	}

	public void setCategories_id(Categories categories_id) {
		this.categories_id = categories_id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getCurrent_stock() {
		return current_stock;
	}

	public void setCurrent_stock(int current_stock) {
		this.current_stock = current_stock;
	}

	public Integer getReorder_stock() {
		return reorder_stock;
	}

	public void setReorder_stock(int reorder_stock) {
		this.reorder_stock = reorder_stock;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public OffsetDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(OffsetDateTime created_at) {
		this.created_at = created_at;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	
	

}
