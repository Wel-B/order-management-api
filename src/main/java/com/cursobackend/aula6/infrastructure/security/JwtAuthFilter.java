package com.cursobackend.aula6.infrastructure.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	
	public JwtAuthFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		
		if (header != null && header.startsWith("Bearer")) {
			
			String token = header.substring(7);
			
			try {
				
				String email = jwtService.extractUsername(token);
				
				String role = jwtService.extractRole(token);
				
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
						email,
						null,
						List.of(new SimpleGrantedAuthority("ROLE_"+ role))
				);
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				
			} catch (ExpiredJwtException e) {
				handleAuthError(response, "Token expired");
			} catch (JwtException e) {
				handleAuthError(response, "Invalid token");
			} catch (BadCredentialsException e) {
				handleAuthError(response, "Invalid credentials");
			} catch (Exception e) {
				handleAuthError(response, "Authentication failed");
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
	private void handleAuthError(HttpServletResponse response, String message) throws IOException {
		SecurityContextHolder.clearContext();
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		
		response.getWriter().write("""
				{
					"error": "%s"
				}
		""".formatted(message));
		
	}
	
}
