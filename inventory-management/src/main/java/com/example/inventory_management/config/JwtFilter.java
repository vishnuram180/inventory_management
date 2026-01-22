package com.example.inventory_management.config;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.inventory_management.Utils.JwtUtil;
import com.example.inventory_management.security.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	    String path = request.getServletPath();
	    return path.equals("/auth/login") || path.equals("/api/login/register")||path.equals("/auth/refresh")||path.startsWith("/api/export/download/")
	    		||path.startsWith("/api/reports/");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		String authHeader=request.getHeader("Authorization");
		String Token=null;
		String Username=null;
		try {
		if(authHeader!=null&&authHeader.startsWith("Bearer ")) {
			Token=authHeader.substring(7);
			Username=jwtUtil.extractUserName(Token);
		}
		if(Username!=null&&SecurityContextHolder.getContext().getAuthentication()==null) {
                  UserDetails userDetail=userDetailsService.loadUserByUsername(Username);
                  
                  if(jwtUtil.isTokenValid(Token, userDetail.getUsername())) {
                	  	UsernamePasswordAuthenticationToken authToken=
                	  			new UsernamePasswordAuthenticationToken(
                	  					userDetail,
                	  					null,
                	  					userDetail.getAuthorities()
                	  					);
                	  	SecurityContextHolder.getContext().setAuthentication(authToken);
                  }
                  System.out.print("------------from filter-----------");

		}
		}
		catch(Exception e) {
			System.out.println("JWT Validation Error: " + e.getMessage());
		}
	

		// Always continue the chain
		filterChain.doFilter(request,response);
	}
	

}
