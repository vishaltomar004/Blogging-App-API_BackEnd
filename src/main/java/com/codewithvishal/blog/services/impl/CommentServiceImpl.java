package com.codewithvishal.blog.services.impl;

import com.codewithvishal.blog.entities.Comment;
import com.codewithvishal.blog.entities.Post;
import com.codewithvishal.blog.exceptions.ResourceNotFoundException;
import com.codewithvishal.blog.payloads.CommentDto;
import com.codewithvishal.blog.repositories.CommentRepo;
import com.codewithvishal.blog.repositories.PostRepo;
import com.codewithvishal.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
	@Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        // TODO Auto-generated method stub
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
        Comment comment =this.modelMapper.map(commentDto , Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment , CommentDto.class) ;
    }

    @Override
    public void deleteComment(Integer commentId) {
        // TODO Auto-generated method stub
              Comment con = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment ", "Comment Id" , commentId) );
			  this.commentRepo.delete(con);
    }
}
