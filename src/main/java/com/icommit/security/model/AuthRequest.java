package com.icommit.security.model;

import java.io.Serializable;

public class AuthRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	public AuthRequest() {		
	}
	
	public AuthRequest(String userName, String password) {
		super();
		this.username = userName;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
