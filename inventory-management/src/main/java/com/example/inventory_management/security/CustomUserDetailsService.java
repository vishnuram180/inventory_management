package com.example.inventory_management.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.inventory_management.Repository.LogInRepository;
import com.example.inventory_management.entity.LogIn;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private LogInRepository loginRepo;

	public CustomUserDetailsService(LogInRepository loginRepo) {
		this.loginRepo = loginRepo;

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("yes 222222 from customDetailService");

		LogIn user = loginRepo.findByUsername(username);

		if (user == null) {
			System.out.println("User not found with username: " + username);
			throw new UsernameNotFoundException("User not found");
		}

		return new CustomUserDetails(user);
	}

}
