package com.icommit.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.icommit.service.JwtUserDetailsService;

import io.jsonwebtoken.Claims;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = null;
		if (header != null && header.startsWith("Bearer ")) {
			token = header.substring(7);
			String username = jwtUtil.getUsernameFromToken(token);
			
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
				
				//Validate the token. If valid, manually create and set the authentication object in security context
				if(jwtUtil.isTokenValid(token, userDetails)) {
					Claims claims = jwtUtil.getAllClaimsFromToken(token);
					List<String> roles = claims.get("role", List.class);
					List<SimpleGrantedAuthority> authorities = new ArrayList<>();
					roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}
