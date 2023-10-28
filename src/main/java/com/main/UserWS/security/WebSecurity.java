package com.main.UserWS.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.main.UserWS.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity {

	@Autowired
	UserService userService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Bean
	protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		httpSecurity.authorizeHttpRequests().requestMatchers(HttpMethod.POST, "/users").permitAll()
				.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll().and()
				.addFilter(new AuthenticationFilter(authenticationManager)).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return httpSecurity.build();

	}
}
