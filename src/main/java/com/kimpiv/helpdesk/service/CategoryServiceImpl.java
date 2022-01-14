package com.kimpiv.helpdesk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kimpiv.helpdesk.model.Category;
import com.kimpiv.helpdesk.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{

	private final CategoryRepository categoryRepository;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Category findByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public Category findById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if(category.isPresent()) {
			return category.get();
		}
		return null;
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

}
