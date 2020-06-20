package com.icommit.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.icommit.model.User;
import com.icommit.security.JWTUtil;
import com.icommit.security.model.AuthRequest;
import com.icommit.security.model.AuthResponse;
import com.icommit.service.JwtUserDetailsService;

@RestController
public class JwtAuthenticationController {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
		logger.info("Executing createAuthenticationToken");
		// First authenticate username and password
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
		try {
			authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (DisabledException de) {
			throw new Exception(de.getMessage());
		} catch (LockedException le) {
			throw new Exception(le.getMessage()); 
		} catch (BadCredentialsException be) {
			throw new Exception(be.getMessage());
		}
		logger.info("Successfully authenticated");
		//After authentication, generate the JWT token and return
		UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authRequest.getUsername());
		if (userDetails != null) {
			String token = jwtUtil.generateToken((User)userDetails);
			return ResponseEntity.ok(new AuthResponse(token));
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
}
