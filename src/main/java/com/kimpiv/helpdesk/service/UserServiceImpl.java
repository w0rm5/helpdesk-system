package com.kimpiv.helpdesk.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kimpiv.helpdesk.model.UserInfo;
import com.kimpiv.helpdesk.model.Role;
import com.kimpiv.helpdesk.repository.RoleRepository;
import com.kimpiv.helpdesk.repository.UserRepository;
import com.kimpiv.helpdesk.service.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserInfo saveUser(UserRegistrationDto regDto) {
		Role role = roleRepository.findByName(regDto.getRole());
		UserInfo user = new UserInfo(regDto.getFirstName(), 
				regDto.getLastName(), regDto.getGender(), 
				LocalDate.parse(regDto.getDateOfBirth()), regDto.getPlaceOfBirth(), 
				regDto.getCurrentAddress(), regDto.getPhone(), 
				regDto.getEmail(), regDto.getUserId(), 
				regDto.getPosition(), regDto.getUnit(), 
				passwordEncoder.encode(regDto.getPassword()), 
				Arrays.asList(role));
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid email or password");
		}
		if(user.isBanned()) {
			throw new UsernameNotFoundException("You have been banned from loggin in. Please contact an administrator.");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> { 
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new User(user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String email, String roleName) {
		UserInfo user = userRepository.findByEmail(email);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
		userRepository.save(user);
	}

	@Override
	public void removeRoleFromUser(String email, String roleName) {
		UserInfo user = userRepository.findByEmail(email);
		user.getRoles().removeIf(role -> role.getName().equals(roleName));
		userRepository.save(user);
	}

	@Override
	public List<UserInfo> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserInfo getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UserInfo getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public UserInfo saveUser(UserInfo user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public UserInfo updateUser(UserRegistrationDto userDto) {
		UserInfo user = getUserById(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setGender(userDto.getGender());
		user.setDateOfBirth(LocalDate.parse(userDto.getDateOfBirth()));
		user.setPlaceOfBirth(userDto.getPlaceOfBirth());
		user.setCurrentAddress(userDto.getCurrentAddress());
		user.setPhone(userDto.getPhone());
		user.setEmail(userDto.getEmail());
		user.setUserId(userDto.getUserId());
		user.setPosition(userDto.getPosition());
		user.setUnit(userDto.getUnit());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		return userRepository.save(user);
	}

}
