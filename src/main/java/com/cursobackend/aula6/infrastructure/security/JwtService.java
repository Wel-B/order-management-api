package com.cursobackend.aula6.infrastructure.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cursobackend.aula6.domain.user.model.Role;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;
	
	private Key key() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public String generateToken(String email, Role role) {
		
		return Jwts.builder()
				.setSubject(email)
				.claim("role", role.name())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
				.signWith(key())
				.compact();	
	}
	
	public String extractUsername(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public String extractRole(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.get("role", String.class);
	}
	
}
