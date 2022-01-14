package com.kimpiv.helpdesk.config;

//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kimpiv.helpdesk.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public SecurityConfiguration(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//		auth.setUserDetailsService(userService);
//		auth.setPasswordEncoder(bCryptPasswordEncoder);
//		return auth;
//	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider());
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/login**", "/registration**", "/css/**", "/js/**").permitAll()
			.antMatchers("/user**").hasAuthority("ROLE_ADMIN")
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout().invalidateHttpSession(true).clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout").permitAll();
	}
	
}
