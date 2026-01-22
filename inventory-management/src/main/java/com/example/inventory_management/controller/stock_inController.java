package com.example.inventory_management.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory_management.Service.stock_inService;
import com.example.inventory_management.config.GetUserId;
import com.example.inventory_management.entity.Stock_In;

@RestController
@RequestMapping("/api/stock_in")
public class stock_inController {
	@Autowired
	private stock_inService SI_service;
	
	

	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getall")
	public List<Stock_In> getall() {
		return SI_service.getall();
	}
	
	
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/get/{id}")
	public Optional<Stock_In> getById(@PathVariable Long id) {
		return SI_service.getById(id);
	}
	
	
	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@PostMapping("/add")
	public Stock_In add(@RequestBody Stock_In stock_in) {
		return SI_service.add(stock_in);
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/my_stock")
	public List<Stock_In> getMine() {
     	System.out.print("controller hit");

		return SI_service.getMine();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update/{id}")
	public Stock_In update(@PathVariable Long id,@RequestBody Stock_In stock_in) {
		return SI_service.update(id,stock_in);
	}
	
	
	
	

}
