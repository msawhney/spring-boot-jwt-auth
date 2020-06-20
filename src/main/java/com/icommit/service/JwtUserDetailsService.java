package com.icommit.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.icommit.model.User;
import com.icommit.security.model.Role;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private Map<String, User> userMap;
	
	@PostConstruct
	public void init() {
		userMap = new HashMap<>();
		//Passwords generated using online Bcrypt https://www.javainuse.com/onlineBcrypt
		userMap.put("user", new User("user", "$2a$10$ZICBIyj1dC2XMEwwtFpne.coicKWhzSKjlswAdZPUA86EP6nHSKCq", true, Arrays.asList(Role.ROLE_USER)));
		userMap.put("admin", new User("admin", "$2a$10$qS483zmse4A.TVul0TSlaunMi21u2OvNknz/VCeV0JXu1dpC81Q0W", true, Arrays.asList(Role.ROLE_ADMIN)));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (userMap.containsKey(username))
			return userMap.get(username);
		else throw new UsernameNotFoundException("User with username " + username + " could  not be found");
	}

}
