package com.example.inventory_management.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.inventory_management.entity.Stock_Logs;

public interface Stock_logsRepository extends JpaRepository<Stock_Logs,Long> {

	@Query(value="SELECT*FROM stock_logs WHERE user_id=:u_id", nativeQuery=true)
	List<Stock_Logs> findByUserId(@Param("u_id")Integer myid);

}