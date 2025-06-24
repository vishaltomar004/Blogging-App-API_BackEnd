package com.codewithvishal.blog.repositories;

import com.codewithvishal.blog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
