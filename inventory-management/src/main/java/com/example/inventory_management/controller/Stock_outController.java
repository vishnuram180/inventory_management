package com.example.inventory_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory_management.Service.Stock_OutService;
import com.example.inventory_management.entity.Stock_In;
import com.example.inventory_management.entity.Stock_Out;

@RestController
@RequestMapping("/api/stock_out")
public class Stock_outController {
	@Autowired
	private Stock_OutService SO_service;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getall")
	public List<Stock_Out> getall() {

		return SO_service.getall();
	}
	
	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@PostMapping("/add")
	public Stock_Out add(@RequestBody Stock_Out stock_out) {
		return SO_service.add(stock_out);
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update/{id}")
	public  Stock_Out update(@PathVariable Long id,@RequestBody Stock_Out stock_out) {
		return SO_service.update(id,stock_out);
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/my_stock")
	public List<Stock_Out> getMine() {
     	System.out.print("controller hit");
		return SO_service.getMine();
	}
}
