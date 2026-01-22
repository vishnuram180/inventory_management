package com.example.inventory_management.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory_management.Service.ProductsService;
import com.example.inventory_management.entity.Products;

@RestController
@RequestMapping("/api/product")
public class ProductsController {
	
	@Autowired
	private ProductsService p_service;
	
	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	@GetMapping("/get")
	public Page<Products> getall(
			@RequestParam (defaultValue="0") int page,
			@RequestParam (defaultValue="5") int size){
		return p_service.getall(page,size);
	}
	
	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	@GetMapping("/getbyid/{id}")
	public Optional<Products> getbyId(@PathVariable Long id) {
		return p_service.getbyId(id);
		
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/add")
	public Products add(@RequestBody Products product ) {
		return p_service.add(product);
		
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update/{id}")
	public Products update(@PathVariable Long id,@RequestBody Products product) {
		return p_service.update(id,product);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/toggle/{id}")
	public Products toggle(@PathVariable Long id) {
		return p_service.toggle(id);
	}
}
