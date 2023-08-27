package com.codewithvishal.blog.services.impl;

import org.springframework.data.domain.*;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.weaver.AjAttribute.PointcutDeclarationAttribute;
import org.aspectj.weaver.ast.Literal;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.codewithvishal.blog.entities.Category;
import com.codewithvishal.blog.entities.Post;
import com.codewithvishal.blog.entities.User;
import com.codewithvishal.blog.exceptions.ResourceNotFoundException;
import com.codewithvishal.blog.payloads.PostDto;
import com.codewithvishal.blog.payloads.PostResponse;
import com.codewithvishal.blog.repositories.CategoryRepo;
import com.codewithvishal.blog.repositories.PostRepo;
import com.codewithvishal.blog.repositories.UserRepo;
import com.codewithvishal.blog.services.PostService;

import ch.qos.logback.core.status.NopStatusListener;
@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private PostRepo postRepo;
     
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
	User user =this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user Id", userId));
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Post post=this.modelMapper.map(postDto, Post.class);
        post.setPostId(postDto.getPostId());
		post.setImageName("default.png");
		post.getAddedDate();
		post.setCategory(category);
		post.setUser(user);
		Post newPost= this.postRepo.save(post);
		return this.modelMapper.map(newPost , PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
	
//		post.setCategory(postDto.getCategory());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageNameString());
		post.setTitle(postDto.getTitle());
		post.setAddedDate(postDto.getAddeDate());
		Post updatedPost=this.postRepo.save(post);
		PostDto updatedPostDto=this.modelMapper.map(updatedPost, PostDto.class);
		return updatedPostDto;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		this.postRepo.delete(post);
		
	}

	

	@Override
	public PostDto getPostById(Integer postId) {
		Post post= this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post id ", postId));
		PostDto postDto= this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","category Id", categoryId));
		List<Post> posts=this.postRepo.findByCategory(cat);
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
				return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User id", userId));
		List<Post> posts=this.postRepo.findByUser(user);
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		org.springframework.data.domain.Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=org.springframework.data.domain.Sort.by(sortBy).ascending();
		}
		else {
			sort=org.springframework.data.domain.Sort.by(sortBy).descending();
		}
		org.springframework.data.domain.PageRequest p= PageRequest.of(pageNumber, pageSize, sort);
		
		 Page<Post>pagePost=this.postRepo.findAll(p);
		
		List<Post> posts=pagePost.getContent();
		
		List<PostDto> postDto= posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class )).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastpage(pagePost.isLast());
		return postResponse;
	}

	
	@Override
	public List<PostDto> searchPosts(String keyword) {
    List<Post> posts=this.postRepo.findByTitleContaining(keyword);
    List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	
	

}
