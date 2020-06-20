package com.icommit.security.model;

import java.io.Serializable;

public class AuthResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	
	public AuthResponse() {		
	}

	public AuthResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
