package com.codewithvishal.blog.services;

import java.util.List;

import com.codewithvishal.blog.entities.Post;
import com.codewithvishal.blog.payloads.PostDto;
import com.codewithvishal.blog.payloads.PostResponse;

public interface PostService {

	
	PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
	
	//update
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all post
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//get single post
	
	PostDto getPostById(Integer postId);
	
	//get all post by category
	
	List<PostDto> getPostByCategory(Integer categoryId);
	// get all posts by user
	
	List<PostDto> getPostsByUser(Integer userId);
	
	//search posts
	List<PostDto> searchPosts(String keyword);
	
}
