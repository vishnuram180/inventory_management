package com.example.inventory_management.Utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private static final String secret = "q1w23e4r5t6y7u8i9oz0s9d8f8srdcfvgbnhjmkldrcfvgbh";

	// private final SecretKey secret_key=Keys.hmacShaKeyFor(secret.getBytes());
	private Key getSignKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String username, Integer id, Set<String> roles) {
		System.out.println("role="+roles);
		return Jwts.builder()
				.claim("ID", id)
				.claim("Roles", roles)
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))// for only 1 hour
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	@SuppressWarnings("deprecation")
	public String extractUserName(String Token) {
		System.out.println("DEBUG: Secret used for parsing: " + secret);
		System.out.println("DEBUG: Token received for parsing: " + Token);
		return Jwts.parser()
				.setSigningKey(getSignKey())
				.parseClaimsJws(Token)
				.getBody()
				.getSubject();
	}
	
	

	public Boolean isTokenValid(String Token, String username) {

		return extractUserName(Token).equals(username) && !isTokenExpired(Token);
	}

	public Boolean isTokenExpired(String Token) {
		Date Expires = Jwts.parser()
				.setSigningKey(getSignKey())
				.parseClaimsJws(Token)
				.getBody()
				.getExpiration();
		return Expires.before(new Date());
	}

}
