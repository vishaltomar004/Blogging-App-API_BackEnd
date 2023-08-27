package com.codewithvishal.blog.payloads;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.codewithvishal.blog.entities.Category;
import com.codewithvishal.blog.entities.Comment;
import com.codewithvishal.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
	private String title;
	
	private String content;
	
	private String imageNameString;
	
	private Date addeDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments= new HashSet<>();
	
	
    
}
