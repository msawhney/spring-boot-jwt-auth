package com.icommit.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icommit.model.Message;

@RestController
public class ResourceController {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
	
	@GetMapping("/resource/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> user() {
		logger.info("user() executing...");
		return ResponseEntity.ok(new Message("User Message"));
	}
	
	@GetMapping("/resource/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> admin() {
		logger.info("admin() Executing...");
		return ResponseEntity.ok(new Message("Admin Message"));
	}
	
	@GetMapping("/resource/user-or-admin")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> userOrAdmin() {
		logger.info("userOrAdmin() Executing...");
		return ResponseEntity.ok(new Message("User or Admin Message"));
	}

}
