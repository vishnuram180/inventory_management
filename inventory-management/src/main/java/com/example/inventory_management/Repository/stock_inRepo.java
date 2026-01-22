package com.example.inventory_management.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.inventory_management.DTO.ReportsDTO;
import com.example.inventory_management.entity.Stock_In;

public interface stock_inRepo extends JpaRepository<Stock_In, Long> {

     @Query(value = "SELECT*FROM stock_in WHERE user_id=:u_id", nativeQuery = true)
     List<Stock_In> findByUserId(@Param("u_id") Integer myid);

     @Query(value = "SELECT  quantity FROM stock_in WHERE recieved_date:=date", nativeQuery = true)
     Integer findyByDate(@Param("date") Date today);

     @Query(value = "SELECT CURRENT_DATE AS Date, p.name AS product_Name, " +
               "COALESCE((SELECT SUM(si.quantity) FROM stock_in si WHERE si.product_id = p.product_id AND si.recieved_date = CURRENT_DATE), 0) AS stock_in, "
               +
               "COALESCE((SELECT SUM(so.quantity) FROM stock_out so WHERE so.product_id = p.product_id AND so.issued_date = CURRENT_DATE), 0) AS stock_out, "
               +
               "p.current_stock AS available_stock " +
               "FROM products p " +
               "WHERE EXISTS (SELECT 1 FROM stock_in si WHERE si.product_id = p.product_id AND si.recieved_date = CURRENT_DATE) "
               +
               "OR EXISTS (SELECT 1 FROM stock_out so WHERE so.product_id = p.product_id AND so.issued_date = CURRENT_DATE)", nativeQuery = true)
     List<ReportsDTO> today_report();

     @Query(value = "SELECT si.recieved_date AS Date, p.name AS product_Name, si.quantity AS stock_in, " +
               "COALESCE((SELECT SUM(so.quantity) FROM stock_out so WHERE so.product_id = p.product_id AND so.issued_date = si.recieved_date), 0) AS stock_out, "
               +
               "p.current_stock AS available_stock " +
               "FROM stock_in si " +
               "JOIN products p ON si.product_id = p.product_id " +
               "WHERE EXTRACT(MONTH FROM si.recieved_date) = EXTRACT(MONTH FROM CURRENT_DATE)", nativeQuery = true)
     List<ReportsDTO> month_report();

     @Query(value = "SELECT si.recieved_date AS Date, p.name AS product_Name, si.quantity AS stock_in, " +
               "COALESCE((SELECT SUM(so.quantity) FROM stock_out so WHERE so.product_id = p.product_id AND so.issued_date = si.recieved_date), 0) AS stock_out, "
               +
               "p.current_stock AS available_stock " +
               "FROM stock_in si " +
               "JOIN products p ON si.product_id = p.product_id " +
               "WHERE EXTRACT(YEAR FROM si.recieved_date) = EXTRACT(YEAR FROM CURRENT_DATE)", nativeQuery = true)
     List<ReportsDTO> year_report();

}
