package com.kimpiv.helpdesk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kimpiv.helpdesk.model.Category;
import com.kimpiv.helpdesk.model.RequestTicket;
import com.kimpiv.helpdesk.model.Role;
import com.kimpiv.helpdesk.service.CategoryService;
import com.kimpiv.helpdesk.service.RequestTicketService;
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
	CommandLineRunner run(UserService userService, CategoryService categoryService, RequestTicketService requestTicketService) {
		return args -> {
			userService.saveRole(new Role("ROLE_ADMIN"));
			userService.saveRole(new Role("ROLE_USER"));
			userService.saveRole(new Role("ROLE_HELPER"));
			
			UserRegistrationDto newAdmin = new UserRegistrationDto(
					"Admin", "Admin", "Male", "1993-10-26", 
					"Phnom Penh", "Phnom Penh", "077460610", 
					"admin@example.com", "ADMIN01", "IT", 
					"Test Unit", "admin123", "admin123", "ROLE_ADMIN");
			
			userService.saveUser(newAdmin);
			userService.addRoleToUser("admin@example.com", "ROLE_HELPER");
			
			UserRegistrationDto newUser = new UserRegistrationDto(
					"User", "Test", "Female", "1995-05-21", 
					"Phnom Penh", "Phnom Penh", "123456789", 
					"user@example.com", "USER01", "IT", 
					"Test Unit", "user123", "user123", "ROLE_USER");
			userService.saveUser(newUser);
			
			newUser = new UserRegistrationDto(
					"User2", "Test", "Female", "1995-05-21", 
					"Phnom Penh", "Phnom Penh", "0123456789", 
					"user2@example.com", "USER01", "IT", 
					"Test Unit", "user123", "user123", "ROLE_USER");
			userService.saveUser(newUser);
			
			Category cate1 = new Category("System-software problems", null);
			categoryService.save(cate1);
			categoryService.save(new Category("Online business tax declaration management system", cate1));
			categoryService.save(new Category("Online tax payment management system", cate1));
			categoryService.save(new Category("Tax sutdy management system", cate1));
			categoryService.save(new Category("Small taxpayer business tax declaration software", cate1));
			Category cate2 = new Category("Technology infrastructure problems", null);
			Category cate3 = new Category("Network", cate2);
			categoryService.save(cate2);
			categoryService.save(cate3);
			categoryService.save(new Category("Computers and printers", cate2));
			categoryService.save(new Category("Table phones", cate2));
			categoryService.save(new Category("Other technical problems", null));
			
			requestTicketService.save(new RequestTicket(userService.getUserByEmail("user@example.com"), null, cate3, "test", null, false, 0));
			requestTicketService.save(new RequestTicket(userService.getUserByEmail("user2@example.com"), null, cate3, "test", null, false, 0));
			requestTicketService.save(new RequestTicket(userService.getUserByEmail("user2@example.com"), null, cate3, "test draft", null, true, 0));
		};
	}
	
}
