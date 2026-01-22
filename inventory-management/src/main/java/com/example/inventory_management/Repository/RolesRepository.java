package com.example.inventory_management.Repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory_management.entity.Roles;



public interface RolesRepository extends JpaRepository<Roles,Integer> {
	Optional<Roles> findByRole( String set);
}

