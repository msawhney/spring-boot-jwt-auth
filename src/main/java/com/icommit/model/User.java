package com.icommit.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.icommit.security.model.Role;

@SuppressWarnings("serial")
public class User implements UserDetails {
	
	private String username;
	private String password;
	private Boolean enabled;
	
	private List<Role> roles;
	
	public User(String username, String password, Boolean enabled, List<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role->authorities.add(new SimpleGrantedAuthority(role.toString())));
		return authorities;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

}
