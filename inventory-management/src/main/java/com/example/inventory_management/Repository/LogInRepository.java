package com.example.inventory_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.inventory_management.entity.LogIn;


@Repository
public interface LogInRepository extends JpaRepository<LogIn,Integer> {
	
	
	@Query(value= "SELECT* FROM log_in_details WHERE username=:uname" ,nativeQuery=true)
	LogIn findByUsername(@Param("uname") String username);

     
}
