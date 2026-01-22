package com.example.inventory_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory_management.Service.Stock_LogsService;
import com.example.inventory_management.entity.Stock_In;
import com.example.inventory_management.entity.Stock_Logs;

@RestController
@RequestMapping("/api/stock_log")
public class Stock_LogsController {
     
	@Autowired
	private Stock_LogsService SL_service;
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getall")
	public List<Stock_Logs> getall(){
		return SL_service.getall();
	}
	

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/my_stock")
	public List<Stock_Logs> getMine() {
     	System.out.print("controller hit");

		return SL_service.getMine();
	}
}
