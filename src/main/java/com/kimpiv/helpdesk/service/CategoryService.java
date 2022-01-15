package com.kimpiv.helpdesk.service;

import java.util.List;

import com.kimpiv.helpdesk.model.Category;

public interface CategoryService {
	Category save(Category category);
	Category findByName(String name);
	Category findById(Long id);
	List<Category> getAllCategories();
	List<Category> findSubCategories(Long id);
}
