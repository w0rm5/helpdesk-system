package com.kimpiv.helpdesk.service.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kimpiv.helpdesk.model.UserInfo;
import com.kimpiv.helpdesk.service.UserService;
import com.kimpiv.helpdesk.service.web.dto.UserRegistrationDto;

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
	
	@GetMapping("role-user/{id}")
	public String changeToRoleUser(@PathVariable("id") Long id) {
		UserInfo user = userService.getUserById(id);
		userService.removeRoleFromUser(user.getEmail(), "ROLE_HELPER");
		userService.addRoleToUser(user.getEmail(), "ROLE_USER");
		return "redirect:/user?rolechange";
	}
	
	@GetMapping("role-helper/{id}")
	public String changeToRoleHelper(@PathVariable("id") Long id) {
		UserInfo user = userService.getUserById(id);
		userService.removeRoleFromUser(user.getEmail(), "ROLE_USER");
		userService.addRoleToUser(user.getEmail(), "ROLE_HELPER");
		return "redirect:/user?rolechange";
	}
	
	@GetMapping("add-admin-role/{id}")
	public String addAdminRole(@PathVariable("id") Long id) {
		UserInfo user = userService.getUserById(id);
		userService.addRoleToUser(user.getEmail(), "ROLE_ADMIN");
		return "redirect:/user?rolechange";
	}
	
	@GetMapping("remove-admin-role/{id}")
	public String removeAdminRole(@PathVariable("id") Long id) {
		UserInfo user = userService.getUserById(id);
		userService.removeRoleFromUser(user.getEmail(), "ROLE_ADMIN");
		return "redirect:/user?rolechange";
	}
	
	@GetMapping("ban/{id}")
	public String banUser(@PathVariable("id") Long id) {
		UserInfo user = userService.getUserById(id);
		user.setBanned(true);
		userService.saveUser(user);
		return "redirect:/user?rolechange";
	}
	
	@GetMapping("{id}")
	public String getUser(@PathVariable("id") Long id, Model model) {
		UserInfo user = userService.getUserById(id);
		if(user == null) {
			model.addAttribute("status", "404");
			model.addAttribute("error", "Not Found");
			return "error";
		}
		UserRegistrationDto userDto = new UserRegistrationDto(id, user.getFirstName(), 
				user.getLastName(), user.getGender(), user.getDateOfBirth().toString(), 
				user.getPlaceOfBirth(), user.getCurrentAddress(), 
				user.getPhone(), user.getEmail(), user.getUserId(), 
				user.getPosition(), user.getUnit(), "", "", "ROLE_USER");
		model.addAttribute("user", userDto);
		return "user_edit";
	}
	
	@PostMapping("save")
	public String saveUser(@ModelAttribute("user") UserRegistrationDto userDto) {
		userService.updateUser(userDto);
		return "redirect:/user?userupdate";
	}
	
}
