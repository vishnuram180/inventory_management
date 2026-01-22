package com.example.inventory_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory_management.Service.SupplierService;
import com.example.inventory_management.entity.Supplier;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
	@Autowired
    private SupplierService s_service;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/get")
	public List<Supplier> get() {
		return  s_service.get();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/add")
	public Supplier add(@RequestBody Supplier supplier) {
		return s_service.add(supplier);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update/{Id}")
	public Supplier update(@PathVariable Long Id,@RequestBody Supplier supplier ) {
		return s_service.update(Id,supplier);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public void Delete(@PathVariable Long id) {
		s_service.delete(id);
	}
}
