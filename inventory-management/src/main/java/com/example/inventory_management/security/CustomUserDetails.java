package com.example.inventory_management.security;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.inventory_management.Enum.Status;
import com.example.inventory_management.entity.LogIn;

public class CustomUserDetails implements UserDetails {

    private final LogIn user;



    public CustomUserDetails(LogIn user) {
        this.user = user;
    }

    public Integer getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var auths= user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
        System.out.println("AUTHORITIES = " + auths);
        return auths;

    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
   
    @Override
    public boolean isEnabled() {
        return user.getStatus() == Status.ACTIVE;
        
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    
	    
}
