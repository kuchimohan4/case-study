package com.cropdeal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cropdeal.filter.JwtValidationFilter;
import com.cropdeal.sevice.customUserDetailservice;
import com.cropdeal.sevice.jwtService;



@Configuration
public class authConfig {
	
	private jwtService jtwService=new jwtService();
	
	@Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

	 return http.csrf().disable().cors().disable() 
			 .addFilterBefore(new JwtValidationFilter(jtwService), UsernamePasswordAuthenticationFilter.class)
	 		.authorizeHttpRequests()
	 		.requestMatchers("/adminControl/getFarmers","/adminControl/changeAccountStatus").hasRole("ADMIN")
			.requestMatchers("/auth/register","/auth/validateMail","/auth/token","/auth/validate").permitAll()
			.anyRequest().permitAll()
			.and().build();
	 
	
}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	
	
	@Bean
	public UserDetailsService userDetailsService() {
	    return	new customUserDetailservice();
	}
	
	

}
