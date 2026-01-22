package com.example.inventory_management.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.inventory_management.DTO.RegisterRequestDTO;
import com.example.inventory_management.Enum.Status;
import com.example.inventory_management.Repository.LogInRepository;
import com.example.inventory_management.Repository.RolesRepository;
import com.example.inventory_management.entity.LogIn;
import com.example.inventory_management.entity.Roles;

@Service
public class LogInService {
     
	private final LogInRepository LogInRepo;
	private final RolesRepository roleRepo;

	private PasswordEncoder passwordEncoder;
	
	public LogInService(LogInRepository LogInRepo,RolesRepository roleRepo, PasswordEncoder passwordEncoder) {
	 this.LogInRepo=LogInRepo;
	 this.roleRepo=roleRepo;
	 this.passwordEncoder=passwordEncoder;
	
	}
	
	public  List<LogIn> getall_LogIn() {
		return LogInRepo.findAll();
	}
	
	public LogIn add(RegisterRequestDTO RegisterRequest) {
		Roles role=roleRepo.findByRole(RegisterRequest.getRole()).orElseThrow(()->new RuntimeException("role not found"));
	
	    LogIn newLogIn=new LogIn();
	   newLogIn.setUsername(RegisterRequest.getUsername());
	   newLogIn.setPassword(passwordEncoder.encode(RegisterRequest.getPassword()));
	   newLogIn.setStatus(Status.ACTIVE);
	   newLogIn.setCreated_at(OffsetDateTime.now());
	   newLogIn.setRoles(Set.of(role));
	   return LogInRepo.save(newLogIn);
	}
	
	public LogIn toggle_status(Integer id){
	   LogIn LogIn=LogInRepo.findById(id).orElseThrow(()->new RuntimeException("LogIn not found"));
	   LogIn.setStatus(LogIn.getStatus()==Status.ACTIVE ?Status.INACTIVE:Status.ACTIVE);
	   return LogInRepo.save(LogIn);
	   
	}
	public void delete_LogIn( Integer id) {
		 LogInRepo.deleteById(id);
		}
}
