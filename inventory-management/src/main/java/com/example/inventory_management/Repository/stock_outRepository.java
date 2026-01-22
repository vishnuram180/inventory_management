package com.example.inventory_management.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.inventory_management.entity.Stock_Out;

public interface stock_outRepository extends JpaRepository<Stock_Out,Long> {

	@Query(value="SELECT*FROM stock_out WHERE user_id=:u_id", nativeQuery=true)
	List<Stock_Out> findByUserId(@Param("u_id")Integer myid);

	@Query(value="SELECT  quantity FROM stock_out WHERE issued_date:=date",nativeQuery=true)
	Integer findyByDate(@Param("date")Date today);	
	
}
