package com.example.inventory_management.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.inventory_management.Repository.ProductsRepository;
import com.example.inventory_management.entity.Products;

@Service
public class ProductsService {
	private ProductsRepository p_repo;
	
	public ProductsService(ProductsRepository p_repo) {
		this.p_repo=p_repo;
	}

	public Page<Products> getall(int page,int size) {
		Pageable pageable=PageRequest.of(page, size);
		return p_repo.findAll(pageable);
	}

	public Products add(Products product) {
		product.setCreated_at(OffsetDateTime.now());
  		return  p_repo.save(product);
	}

	public Products update(Long id, Products product) {
		@SuppressWarnings("deprecation")
		Products newproduct=p_repo.getById(id);
		newproduct.setPrice(product.getPrice());
		newproduct.setReorder_stock(product.getReorder_stock());
		return p_repo.save(newproduct);
	}

	public Products toggle(Long id) {
		Products tog=p_repo.findById(id).orElseThrow(()-> new RuntimeException("product not found"));
		tog.setIs_active(tog.isIs_active()==true?false:true);
		return p_repo.save(tog);
	}

	public Optional<Products> getbyId(Long id) {
		
		return p_repo.findById(id);
	}

}
