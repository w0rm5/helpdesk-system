package com.kimpiv.helpdesk.service.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kimpiv.helpdesk.model.Category;
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
	
	@GetMapping("{id}")
	public String saveCategory(@PathVariable("id") String id, Model model) {
		if(id.equals("new")) {			
			model.addAttribute("category", new Category());
		} else {
			try {				
				Long catId = Long.parseLong(id);
				Category editCat = categoryService.findById(catId);
				if(editCat == null) {
					model.addAttribute("status", "404");
					model.addAttribute("error", "Not Found");
					return "error";
				}
				model.addAttribute("category", editCat);
			} catch (NumberFormatException e) {
				model.addAttribute("status", "404");
				model.addAttribute("error", "Not Found");
				return "error";
			}
		}
		model.addAttribute("mainCategories", categoryService.findSubCategories(null));
		return "category_edit";
	}
	
	@PostMapping("save")
	public String saveCategory(@ModelAttribute("category") Category category) {
		Category c = categoryService.findByName(category.getName());
		if(c != null) {
			if(category.getId() == null || category.getId() != c.getId() ) {
				return "redirect:/category/" + (category.getId() == null ? "new" : category.getId().toString()) + "?errorname";
			}
		}
		categoryService.save(category);
		return "redirect:/category?saved";
	}
	
	@GetMapping("sub/{id}")
	@ResponseBody
	public List<Category> getSubCategories(@PathVariable("id") Long id) {
		return categoryService.findSubCategories(id);
	}
}
