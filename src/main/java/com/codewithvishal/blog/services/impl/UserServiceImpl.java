package com.codewithvishal.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.codewithvishal.blog.config.AppConstants;
import com.codewithvishal.blog.entities.Role;
import com.codewithvishal.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithvishal.blog.entities.User;
import com.codewithvishal.blog.exceptions.ResourceNotFoundException;
import com.codewithvishal.blog.payloads.UserDto;
import com.codewithvishal.blog.repositories.UserRepo;
import com.codewithvishal.blog.services.UserService;
@Service
public class UserServiceImpl implements UserService{
      
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto , User.class);
		// Encoded password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		//roles
		Role role =this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser , UserDto.class);
	}

	@Override
	public UserDto createUser(UserDto userDto) {
	        User user=this.dtoToUser(userDto);
	        User savedUser=this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
	               User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser=this.userRepo.save(user);
		UserDto userDto1=this.userToDto(updatedUser);
		return userDto1;
		
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		 List<User> users=this.userRepo.findAll();
		 
		List<UserDto> userDtos=users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stubl̥
		
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
		
	}
	
	private User dtoToUser(UserDto userDto) {
	
		User user=this.modelMapper.map(userDto, User.class);
		return user;
		
		
		
		
		
		
		
		// Without Model mapper 
//		User user =new User();
//		 user.setId(userDto.getId());
//		 user.setName(userDto.getName());
//		 user.setEmail(userDto.getEmail());
//		 user.setAbout(userDto.getAbout());
//		 user.setPassword(userDto.getPassword());
//		 return user;
		 
		
	}
	
	private UserDto userToDto(User user) {
		
		
		
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		return userDto;
		
//		UserDto userDto= new UserDto();
//		userDto.setId(user.getId());
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
//		return  userDto;
	}

	
	

}
