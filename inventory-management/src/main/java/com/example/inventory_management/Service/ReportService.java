package com.example.inventory_management.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.inventory_management.DTO.ReportsDTO;
import com.example.inventory_management.Repository.ProductsRepository;
import com.example.inventory_management.Repository.stock_inRepo;
import com.example.inventory_management.Repository.stock_outRepository;

@Service
public class ReportService {
	
	private stock_inRepo SI_repo;
	private stock_outRepository SO_repo;
	private ProductsRepository p_repo;

	public ReportService(stock_inRepo sI_repo, ProductsRepository p_repo,stock_outRepository SO_repo) {	
		this.SI_repo = sI_repo;
		this.p_repo = p_repo;
		this.SO_repo=SO_repo;
	}


	public List<ReportsDTO> get() {		
	   return SI_repo.today_report();
	}


	public List<ReportsDTO> getMonth() {
		return SI_repo.month_report();
	}
	
	public List<ReportsDTO> getYear() {
		return SI_repo.year_report();
	}



	

}
