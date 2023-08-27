package com.codewithvishal.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvishal.blog.entities.User;
public interface UserRepo extends JpaRepository<User, Integer> {

}
