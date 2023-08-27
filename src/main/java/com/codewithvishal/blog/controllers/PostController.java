package com.codewithvishal.blog.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.PutExchange;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.codewithvishal.blog.config.AppConstants;
import com.codewithvishal.blog.entities.Post;
import com.codewithvishal.blog.payloads.ApiResponse;
import com.codewithvishal.blog.payloads.PostDto;
import com.codewithvishal.blog.payloads.PostResponse;
import com.codewithvishal.blog.services.FileService;
import com.codewithvishal.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {
    
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	
	@Value("project.images")
	private String path;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){ 
		
		PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.OK);
		
	}
	
	//get By user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
{
              List<PostDto> posts=this.postService.getPostsByUser(userId);
              return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		}
	
	//get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId)
{
              List<PostDto> posts=this.postService.getPostByCategory(categoryId);
              return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		}
	// get All posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false)String sortDir)
	{
		
		PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	// get post details by Id
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto post=this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	//delete post
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully Deleted",true),HttpStatus.OK);
	}
	// update post
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto postdto1=this.postService.updatePost(postDto, postId);
		return  new ResponseEntity<PostDto>(postdto1,HttpStatus.OK);
	}
	
	// search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable("keywords") String keyword){
	            
		List<PostDto> result=this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
			                                          @PathVariable Integer postId) throws IOException{
		 PostDto postDto=this.postService.getPostById(postId);
		String fileName=this.fileService.uploadImage(path, image);
	
		 postDto.setImageNameString(fileName);
		PostDto updatedPost= this.postService.updatePost(postDto, postId);
		 return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	 // method to serve Files
    @GetMapping(value = "post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloladImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {

        InputStream resource=this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(resource,response.getOutputStream());
        org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());

    }
	
}
