package com.codewithvishal.blog.payloads;

import java.util.List;

import org.aspectj.weaver.ast.Literal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
      
	
	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean isLastpage;
	
}
