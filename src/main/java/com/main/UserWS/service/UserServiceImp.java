package com.main.UserWS.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.main.UserWS.Repository.UserEntity;
import com.main.UserWS.Repository.UserRepository;
import com.main.UserWS.shared.UserDto;
@Service
public class UserServiceImp implements UserService{

	@Autowired
	 UserRepository userRepository;
	
	@Override
	public UserDto createUser(UserDto userDto)
	{   
		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncryptedPassword("hello");
		ModelMapper modelMapper = new ModelMapper(); 
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity= modelMapper.map(userDto, UserEntity.class);
		userRepository.save(userEntity);
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class); 
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  UserEntity userEntity=userRepository.findByEmail(username);
	  if(userEntity==null)
	  {
		  throw new UsernameNotFoundException(username);
	  }
	  
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity= userRepository.findByEmail(email);
		if(userEntity==null)throw new UsernameNotFoundException(email);
		ModelMapper mapper=new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDetails =mapper.map(userEntity, UserDto.class);
		return userDetails;
	}

	

}
