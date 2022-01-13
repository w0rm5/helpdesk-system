package com.kimpiv.helpdesk.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kimpiv.helpdesk.model.UserInfo;
import com.kimpiv.helpdesk.model.Role;
import com.kimpiv.helpdesk.repository.UserRepository;
import com.kimpiv.helpdesk.service.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserInfo save(UserRegistrationDto regDto) {
		UserInfo user = new UserInfo(regDto.getFirstName(), 
				regDto.getLastName(), regDto.getGender(), 
				LocalDate.parse(regDto.getDateOfBirth()), regDto.getPlaceOfBirth(), 
				regDto.getCurrentAddress(), regDto.getPhone(), 
				regDto.getEmail(), regDto.getUserId(), 
				regDto.getPosition(), regDto.getUnit(), 
				passwordEncoder.encode(regDto.getPassword()), 
				Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid email or password");
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> { 
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
	
		return new User(user.getEmail(), user.getPassword(), authorities);
	}

}
