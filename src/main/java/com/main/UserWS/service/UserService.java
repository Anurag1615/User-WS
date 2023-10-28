package com.main.UserWS.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.main.UserWS.shared.UserDto;



public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto userDetails);
	UserDto getUserDetailsByEmail(String email);
}
