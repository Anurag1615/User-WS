package com.main.UserWS.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.main.UserWS.shared.UserDto;

import jakarta.validation.constraints.Email;

public interface UserService extends UserDetailsService{
    UserDto createUser(UserDto userDto);
    UserDto getUserDetailsByEmail(String email);
   
}
