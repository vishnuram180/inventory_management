package com.example.inventory_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory_management.entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories,Long> {

}
