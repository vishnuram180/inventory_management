package com.example.inventory_management.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory_management.DTO.AuthResponseDto;
import com.example.inventory_management.DTO.RegisterRequestDTO;
import com.example.inventory_management.DTO.RequestRefreshTokenDTO;
import com.example.inventory_management.Repository.LogInRepository;
import com.example.inventory_management.Repository.RefreshTokenRepository;
import com.example.inventory_management.Service.RefreshTokenService;
import com.example.inventory_management.Utils.JwtUtil;
import com.example.inventory_management.config.GetUserId;
import com.example.inventory_management.entity.LogIn;
import com.example.inventory_management.entity.RefreshToken;
import com.example.inventory_management.entity.Roles;
import com.example.inventory_management.security.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	public AuthenticationManager authenticationManager;
	
	@Autowired
	public RefreshTokenService RT_service;
	

	@Autowired
	public CustomUserDetailsService userDetailService;
	
	private final LogInRepository LogInRepo;
	private final RefreshTokenRepository  RT_repo;
	
	public AuthController( LogInRepository LogInRepo,RefreshTokenRepository  RT_repo) {
		this.LogInRepo=LogInRepo;
		this.RT_repo=RT_repo;
	}

	
	
	@Autowired
	public JwtUtil jwtUtil;
	
	@Autowired 
	public GetUserId getID;
	
	
	@PostMapping("/login")
	public AuthResponseDto login(@RequestBody RegisterRequestDTO request ){
		System.out.println("yes   1111111 from AuthController");
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()
						)
                        );
		String uname=request.getUsername();
		LogIn user=LogInRepo.findByUsername(uname);
        
       
		Set<String> roleSet = user.getRoles()
		        .stream()
		        .map(Roles::getRole) // extracts "ADMIN", "USER"
		        .collect(Collectors.toSet());
		
		String Token=jwtUtil.generateToken(request.getUsername(),user.getId(),roleSet);
//		System.out.println(user.getAuthorities());
		
		String Refresh_Token=RT_service.create_refresh_token(user.getId());
		
		return new AuthResponseDto(Token,Refresh_Token);
	}
	
	
	@PostMapping("/refresh")
	public AuthResponseDto refresh(@RequestBody RequestRefreshTokenDTO request) {
		System.out.println("Searching for Token: [" + request.getRefresh_Token() + "]");
		RefreshToken refreshToken=RT_repo.findByToken(request.getRefresh_Token()).orElseThrow(()->new RuntimeException("Token Not Found"));
		
		RT_service.verifyIsTokenExpired(refreshToken);
		
		LogIn user=refreshToken.getUser();
		LogIn user_role=LogInRepo.findByUsername(user.getUsername());

		
		 
		Set<String> roleSet = user_role.getRoles()
		        .stream()
		        .map(Roles::getRole) // extracts "ADMIN", "USER"
		        .collect(Collectors.toSet());
		String newAccessToken=jwtUtil.generateToken( user.getUsername(),user.getId(),roleSet);
		
		return new AuthResponseDto(
				newAccessToken,
				refreshToken.getToken()
				
				);
		
	}
	
	


}
