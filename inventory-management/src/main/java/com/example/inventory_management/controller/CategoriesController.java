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

import com.example.inventory_management.Service.categoriesService;
import com.example.inventory_management.entity.Categories;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
	@Autowired
	private categoriesService c_service;

	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	@GetMapping("/getall")
	public List<Categories> getall() {
		return c_service.getcategories();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/add")
	public Categories add(@RequestBody Categories categories) {
		return c_service.add(categories);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update/{id}")
	public Categories update(@PathVariable Long id, @RequestBody Categories categories) {
		return c_service.update(id, categories);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/del/{id}")
	public void delete(@PathVariable Long id) {
		c_service.del(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/toggle/{id}")
	public Categories toggle(@PathVariable Long id) {
		return c_service.toggle(id);
	}

}
