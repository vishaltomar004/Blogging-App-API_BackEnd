package com.codewithvishal.blog.controllers;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithvishal.blog.payloads.ApiResponse;
import com.codewithvishal.blog.payloads.UserDto;
import com.codewithvishal.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	 private UserService userService;
	//POST -create user
	 @GetMapping("check")
	 public String check() {
		 return "this is working";
	 }
	 
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser( @ Valid @RequestBody UserDto userDto){
	UserDto createUserDto=	this.userService.createUser(userDto);
	return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	
	}
	
	//Put- update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
		 UserDto updatedUser=this.userService.updateUser(userDto, userId);
		 return ResponseEntity.ok(updatedUser);
		
	}
	
	//delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted SUCCESSFully",true),HttpStatus.OK);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	
}
	//get
