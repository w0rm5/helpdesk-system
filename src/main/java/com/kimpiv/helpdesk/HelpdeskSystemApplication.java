package com.kimpiv.helpdesk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kimpiv.helpdesk.model.Category;
import com.kimpiv.helpdesk.model.Role;
import com.kimpiv.helpdesk.service.CategoryService;
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
	CommandLineRunner run(UserService userService, CategoryService categoryService) {
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
			
			Category cate1 = new Category("System-software problems", null);
			categoryService.save(cate1);
			categoryService.save(new Category("Online business tax declaration management system", cate1));
			categoryService.save(new Category("Online tax payment management system", cate1));
			categoryService.save(new Category("Tax sutdy management system", cate1));
			categoryService.save(new Category("Small taxpayer business tax declaration software", cate1));
			Category cate2 = new Category("Technology infrastructure problems", null);
			categoryService.save(cate2);
			categoryService.save(new Category("Network", cate2));
			categoryService.save(new Category("Computers and printers", cate2));
			categoryService.save(new Category("Table phones", cate2));
			categoryService.save(new Category("Other technical problems", null));
		};
	}
	
}
