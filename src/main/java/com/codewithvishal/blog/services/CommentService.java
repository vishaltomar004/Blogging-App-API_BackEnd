package com.codewithvishal.blog.services;

import com.codewithvishal.blog.payloads.CommentDto;

public interface CommentService {
   
	CommentDto createComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);
}
