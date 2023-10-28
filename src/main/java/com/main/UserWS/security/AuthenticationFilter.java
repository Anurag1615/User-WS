package com.main.UserWS.security;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.UserWS.Model.LoginRequestModel;
import com.main.UserWS.service.UserService;
import com.main.UserWS.shared.UserDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Autowired 
	UserService userService;

public AuthenticationFilter(AuthenticationManager authenticationManager) {
	super(authenticationManager);
	// TODO Auto-generated constructor stub
}
public Authentication attemptAuthentication (HttpServletRequest request,HttpServletResponse response)
{
	LoginRequestModel cred = null;
	try {
		cred = new ObjectMapper().readValue(request.getInputStream(),LoginRequestModel.class);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(cred.getEmail(),cred.getPassword(),new ArrayList<>()));
}
@Override
protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
		Authentication auth) throws IOException, ServletException {


	String userName = ((User) auth.getPrincipal()).getUsername();
	UserDto userDetails = userService.getUserDetailsByEmail(userName);
}
}