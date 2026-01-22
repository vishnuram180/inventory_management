package com.example.inventory_management.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class RefreshToken {
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Instant getExipryDate() {
		return ExpiryDate;
	}

	public void setExipryDate(Instant instant) {
		ExpiryDate = instant;
	}

	public LogIn getUser() {
		return user;
	}

	public void setUser(LogIn user) {
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	public RefreshToken() {
		
	}

	public RefreshToken(Long id, String token, Instant exipryDate, LogIn user) {
		super();
		this.id = id;
		this.token = token;
		ExpiryDate = exipryDate;
		this.user = user;
	}

	@Column(nullable=false,unique=true)
	private String token;
	
	@Column(nullable=false)
	private Instant ExpiryDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private LogIn  user;

}
