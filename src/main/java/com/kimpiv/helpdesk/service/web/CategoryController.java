package com.kimpiv.helpdesk.service.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kimpiv.helpdesk.service.CategoryService;

@Controller
@RequestMapping("category")
public class CategoryController {
	
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public String viewAllCategories(Model model) {
		model.addAttribute("mainCategories", categoryService.getAllCategories());
		return "category";
	}
}
