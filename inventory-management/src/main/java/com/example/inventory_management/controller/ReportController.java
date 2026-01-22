package com.example.inventory_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory_management.DTO.ReportsDTO;
import com.example.inventory_management.Service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
	
	@Autowired
	private ReportService RT_service;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/today")
	private List<ReportsDTO> get() {
		return  RT_service.get();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/month")
	private List<ReportsDTO> getMonth() {
		return  RT_service.getMonth();
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/year")
	private List<ReportsDTO> getYear() {
		return  RT_service.getYear();
	}
	
	

}
