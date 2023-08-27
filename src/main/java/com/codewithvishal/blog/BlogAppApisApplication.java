package com.codewithvishal.blog;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.springframework.boot.SpringApplication.*;

import org.modelmapper.ModelMapper;

@SpringBootApplication
public class BlogAppApisApplication {

	public static void main(String[] args) {
		run(BlogAppApisApplication.class, args);
	}

	
	@Bean
	 public ModelMapper modelMapper() {
		 return new ModelMapper();
	 }

}
