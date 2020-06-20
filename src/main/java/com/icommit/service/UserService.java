package com.icommit.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.icommit.model.User;
import com.icommit.security.model.Role;

public class UserService {
	
	// Hard coded values
	//TODO Replace with actual implementation
	
	private Map<String, User> userMap;
	
	@PostConstruct
	public void init() {
		userMap = new HashMap<>();
		userMap.put("user", new User("user", "", true, Arrays.asList(Role.ROLE_USER)));
		userMap.put("admin", new User("admin", "", true, Arrays.asList(Role.ROLE_ADMIN)));
	}
	
	public User findByUserName(String username) {
		if (userMap.containsKey(username))
			return userMap.get(username);
		return null;
	}

}
