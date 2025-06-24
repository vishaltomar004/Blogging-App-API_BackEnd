package com.codewithvishal.blog.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.codewithvishal.blog.entities.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int Id;
	@NotEmpty
	@Size(min=4,message="UserName must be minimum of 4 characters")
	private String name;
	@Email(message="Email is not valid!!")
	private String email;
	@NotEmpty
	@Size(min=3,max=10,message="Password must be minimum of 3 character and max of 10")
	private String password;
	@NotEmpty
	private String about;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Post> posts=new ArrayList<>();

	private Set<RoleDto> roles = new HashSet<>();

}
