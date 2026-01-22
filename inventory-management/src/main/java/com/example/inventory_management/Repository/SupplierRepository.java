package com.example.inventory_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inventory_management.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long>{

}
