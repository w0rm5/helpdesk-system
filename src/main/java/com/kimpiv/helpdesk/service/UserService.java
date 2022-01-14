package com.kimpiv.helpdesk.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.kimpiv.helpdesk.model.Role;
import com.kimpiv.helpdesk.model.UserInfo;
import com.kimpiv.helpdesk.service.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	UserInfo saveUser(UserRegistrationDto registrationDto);
	Role saveRole(Role role);
	void addRoleToUser(String email, String roleName);
	void removeRoleFromUser(String email, String roleName);
	List<UserInfo> getUsers();
}
