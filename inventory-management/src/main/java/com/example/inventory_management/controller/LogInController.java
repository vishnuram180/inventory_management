package com.example.inventory_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory_management.DTO.RegisterRequestDTO;
import com.example.inventory_management.Service.LogInService;
import com.example.inventory_management.entity.LogIn;

@RestController
@RequestMapping("/api/login")
@CrossOrigin("*")
public class LogInController {

	@Autowired
	private LogInService LogInservice;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getall")
	public List<LogIn> getall_LogIn() {
		return LogInservice.getall_LogIn();
	}

	@PostMapping("/register")
	public LogIn add_LogIn(@RequestBody RegisterRequestDTO registerRequest) {
		return LogInservice.add(registerRequest);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("toggle/{id}")
	public LogIn status_update(@PathVariable Integer id) {
		return LogInservice.toggle_status(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("delete/{id}")
	public void del_LogIn(@PathVariable Integer id) {
		LogInservice.delete_LogIn(id);
	}

}
