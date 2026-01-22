package com.example.inventory_management.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.inventory_management.Repository.LogInRepository;
import com.example.inventory_management.Repository.ProductsRepository;
import com.example.inventory_management.Repository.SupplierRepository;
import com.example.inventory_management.Repository.stock_inRepo;
import com.example.inventory_management.config.GetUserId;
import com.example.inventory_management.entity.LogIn;
import com.example.inventory_management.entity.Products;
import com.example.inventory_management.entity.Stock_In;
import com.example.inventory_management.entity.Supplier;

@Service
public class stock_inService {
	private GetUserId getID;
	private stock_inRepo SI_repo;
	private ProductsRepository p_repo;
	private SupplierRepository s_repo;
	private LogInRepository loginRepo;
;

	
	public stock_inService (stock_inRepo SI_repo, ProductsRepository p_repo,SupplierRepository s_repo,LogInRepository loginRepo,GetUserId getID) {
		this.SI_repo=SI_repo;
		this.p_repo=p_repo;
		this.s_repo=s_repo;
		this.loginRepo=loginRepo;
		this.getID=getID;
	}
	  


	public List<Stock_In> getall() {
	return SI_repo.findAll();
	}

	public Stock_In add(Stock_In stock_in) {
		Products product=p_repo.findById(
				stock_in.getProduct_id().getProduct_id()).orElseThrow(()->new RuntimeException("product not found"));
		
		LogIn user=loginRepo.findById(
				stock_in.getRecieved_by().getId()).orElseThrow(()->new RuntimeException("user not found"));
		
		Supplier supplier=s_repo.findById(
				stock_in.getSupplier_id().getSupplier_id()).orElseThrow(()->new RuntimeException("supplier not found"));
		
		stock_in.setRecieved_date(LocalDate.now());
		stock_in.setProduct_id(product);
		stock_in.setSupplier_id(supplier);
		stock_in.setRecieved_by(user);
		return SI_repo.save(stock_in);
	}



	public Stock_In update(Long id, Stock_In stock_in) {
        Stock_In upstock=SI_repo.findById(id).orElseThrow(()->new RuntimeException("stock_in not found"));
        
	    stock_in.setId(id);
	    stock_in.setRecieved_date(upstock.getRecieved_date());
		return SI_repo.save(stock_in);
	}



	public List<Stock_In> getMine() {
	    Integer Myid=getID.getuseId();
		return  SI_repo.findByUserId(Myid) ;
	}



	public Optional<Stock_In> getById(Long id) {
		return SI_repo.findById(id);
	}
	
	
	

}
