package com.kimpiv.helpdesk.service.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kimpiv.helpdesk.model.UserInfo;
import com.kimpiv.helpdesk.repository.UserRepository;
import com.kimpiv.helpdesk.service.UserService;
import com.kimpiv.helpdesk.service.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
	
	private final UserService userService;
	private final UserRepository userRepository;

	public UserRegistrationController(UserService userService, UserRepository userRepository) {
		super();
		this.userService = userService;
		this.userRepository = userRepository;
	}
	
	@GetMapping
	public String getRegistrationForm(Model model) {
		model.addAttribute("newUser", new UserRegistrationDto());
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("newUser") UserRegistrationDto registrationDto) {
		UserInfo user = userRepository.findByEmail(registrationDto.getEmail());
		if(user != null) {
			return "redirect:/registration?emailerror";
		}
		user = userRepository.findByPhone(registrationDto.getPhone());
		if(user != null) {
			return "redirect:/registration?phoneerror";
		}
		if(!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
			return "redirect:/registration?passworderror";
		}
		userService.saveUser(registrationDto);
		return "redirect:/login?registrationsuccess";
	}
}
