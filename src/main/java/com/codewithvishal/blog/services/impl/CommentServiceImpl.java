package com.codewithvishal.blog.services.impl;

import org.springframework.stereotype.Service;

import com.codewithvishal.blog.payloads.CommentDto;
import com.codewithvishal.blog.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService{

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		
	}
}
