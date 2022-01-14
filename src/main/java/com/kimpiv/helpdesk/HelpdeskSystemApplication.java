package com.kimpiv.helpdesk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kimpiv.helpdesk.model.Role;
import com.kimpiv.helpdesk.model.UserInfo;
import com.kimpiv.helpdesk.service.UserService;
import com.kimpiv.helpdesk.service.web.dto.UserRegistrationDto;

@SpringBootApplication
public class HelpdeskSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskSystemApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role("ROLE_ADMIN"));
			userService.saveRole(new Role("ROLE_USER"));
			userService.saveRole(new Role("ROLE_HELPER"));
			
			UserRegistrationDto newAdmin = new UserRegistrationDto(
					"Kimpiv", "Lay", "Male", "1993-10-26", 
					"Phnom Penh", "Phnom Penh", "077460610", 
					"laykimpiv@gmail.com", "USER01", "IT", 
					"Test Unit", "admin123", "admin123", "ROLE_USER");
			
			userService.saveUser(newAdmin);
			userService.addRoleToUser("laykimpiv@gmail.com", "ROLE_ADMIN");
		};
	}
	
}
