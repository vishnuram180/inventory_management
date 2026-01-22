package com.example.inventory_management.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inventory_management.entity.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long>{

	Page<Products> findAll(Pageable pagable);
	

}
