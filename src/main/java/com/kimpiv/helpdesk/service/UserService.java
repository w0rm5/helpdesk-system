package com.kimpiv.helpdesk.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.kimpiv.helpdesk.model.UserInfo;
import com.kimpiv.helpdesk.service.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	UserInfo save(UserRegistrationDto registrationDto);
}
