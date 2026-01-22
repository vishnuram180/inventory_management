package com.example.inventory_management.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.inventory_management.Repository.Stock_logsRepository;
import com.example.inventory_management.config.GetUserId;
import com.example.inventory_management.entity.Stock_In;
import com.example.inventory_management.entity.Stock_Logs;

@Service
public class Stock_LogsService {
    private Stock_logsRepository stock_logsRepo;
	private GetUserId getID;

    
    public Stock_LogsService (Stock_logsRepository stock_logsRepo,GetUserId getID) {
    	this.stock_logsRepo=stock_logsRepo;
    	this.getID=getID;
    }

	public List<Stock_Logs> getall() {
		return stock_logsRepo.findAll();
	}

	public List<Stock_Logs> getMine() {
	    Integer Myid=getID.getuseId();
		return  stock_logsRepo.findByUserId(Myid) ;

	}
    
}
