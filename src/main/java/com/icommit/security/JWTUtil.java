package com.icommit.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.icommit.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secretString;

	@Value("${jwt.expirationTime}")
	private String expirationTime;

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secretString.getBytes());
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}	
	
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
	
	public Date getExpirateDateFromToken(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}
	
	public String getUsernameFromToken(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}
	
	public Boolean isTokenExpired(String token) {
		Date expirationDate = getExpirateDateFromToken(token);
		return expirationDate.before(new Date());
	}
	
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		String usernameFromToken = getUsernameFromToken(token);
		return usernameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRoles());
		Date createdDate = new Date();
		Long expirationDateLong = Long.parseLong(expirationTime);
		Date expirationDate = new Date(createdDate.getTime() + expirationDateLong * 1000);
		return Jwts.builder()
		.setSubject(user.getUsername())
		.claim("role", user.getRoles())
		.setIssuedAt(createdDate)
		.setExpiration(expirationDate)
		.signWith(key).compact();
	}

}
