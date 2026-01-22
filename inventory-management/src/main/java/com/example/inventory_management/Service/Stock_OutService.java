package com.example.inventory_management.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.inventory_management.Repository.stock_outRepository;
import com.example.inventory_management.config.GetUserId;
import com.example.inventory_management.entity.Stock_In;
import com.example.inventory_management.entity.Stock_Out;

@Service
public class Stock_OutService {
	private stock_outRepository SO_repo;
	private GetUserId getID;

	
	public Stock_OutService(stock_outRepository SO_repo, GetUserId getID) {
		this.SO_repo=SO_repo;
		this.getID=getID;
	}
	

	public List<Stock_Out> getall() {
		return SO_repo.findAll() ;
	}

	public Stock_Out add(Stock_Out stock_out) {
		stock_out.setIssued_date(LocalDate.now());
		Stock_Out saved=SO_repo.save(stock_out);
		return saved;
	}

	public Stock_Out update(Long id, Stock_Out stock_out) {
		Stock_Out upstock=SO_repo.findById(id).orElseThrow(()->new RuntimeException("stock not found"));
         upstock.setQuantity(stock_out.getQuantity());	
         upstock.setIssued_to(stock_out.getIssued_to());
		return SO_repo.save(upstock);
	}


	public List<Stock_Out> getMine() {
	    Integer Myid=getID.getuseId();
		return  SO_repo.findByUserId(Myid) ;
	}

}
