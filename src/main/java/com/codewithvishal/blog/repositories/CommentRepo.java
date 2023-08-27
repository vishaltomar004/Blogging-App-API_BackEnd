package com.codewithvishal.blog.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codewithvishal.blog.entities.Comment;

public interface CommentRepo extends CrudRepository<Comment, Integer>{

}
