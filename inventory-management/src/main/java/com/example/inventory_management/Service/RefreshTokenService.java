package com.example.inventory_management.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventory_management.Repository.LogInRepository;
import com.example.inventory_management.Repository.RefreshTokenRepository;
import com.example.inventory_management.entity.LogIn;
import com.example.inventory_management.entity.RefreshToken;


@Service
public class RefreshTokenService {
	
	@Autowired
	private RefreshTokenRepository RF_repo;

	@Autowired
	private LogInRepository LI_repo;
	
	
	
	public String create_refresh_token(Integer user_id) {
		
		LogIn user=LI_repo.findById(user_id).orElseThrow(()->new RuntimeException("user id not found"));
		System.out.print("username="+user.getUsername());
		
		RefreshToken RF_token=RF_repo.findbyUser(user_id);
		if(RF_token!=null) {		
               RF_repo.deletebyUser(user_id);
            }
		
		RefreshToken token=new RefreshToken();
		token.setUser(user);
		System.out.print(token.getUser());
		token.setToken(UUID.randomUUID().toString());
		token.setExipryDate( Instant.now().plusMillis(1000L*60*60*24*7));
		
		 
	    RF_repo.save(token);
	    return token.getToken();
		
	}
	
	public RefreshToken verifyIsTokenExpired(RefreshToken token) {
		if(token.getExipryDate().isBefore(Instant.now())) {
			RF_repo.delete(token);
			throw new RuntimeException("Refresh Token has been expired");
		}
		return token;
		
	}
		
	
}
