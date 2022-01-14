package com.kimpiv.helpdesk.service.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kimpiv.helpdesk.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping
	public String viewAllCategories(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "user_list";
	}
}
