package com.codewithvishal.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithvishal.blog.entities.Category;
import com.codewithvishal.blog.exceptions.ResourceNotFoundException;
import com.codewithvishal.blog.payloads.CategoryDto;
import com.codewithvishal.blog.repositories.CategoryRepo;
import com.codewithvishal.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		 Category cat=this.modelMapper.map(categoryDto, Category.class);
		 Category addedCaqt=this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCaqt, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Cstegory Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCat= this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		
		Category cat=this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","Category id", categoryId));
		this.categoryRepo.delete(cat);
	
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
    Category cat=this.categoryRepo.findById(categoryId).
    		orElseThrow(()-> new ResourceNotFoundException("Category", "Categpry Id", categoryId));
		return  this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
	   List<Category> categories=	this.categoryRepo.findAll();
List<CategoryDto>  categoryDtos= categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

}
