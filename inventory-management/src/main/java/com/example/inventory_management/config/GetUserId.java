package com.example.inventory_management.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.example.inventory_management.security.CustomUserDetails;

@Component
public class GetUserId {	
	public Integer getuseId() {
	   Authentication auth=SecurityContextHolder.getContext().getAuthentication();
	  CustomUserDetails user=  (CustomUserDetails) auth.getPrincipal();
	 
	  return user.getId();
	
	}





	
	

}
