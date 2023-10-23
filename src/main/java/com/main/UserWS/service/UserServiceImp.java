package com.main.UserWS.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
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

}
