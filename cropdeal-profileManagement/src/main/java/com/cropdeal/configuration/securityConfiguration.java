package com.cropdeal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.cropdeal.filter.JwtValidationFilter;
import com.cropdeal.util.jwtUtilservice;


@Configuration
@EnableWebSecurity
public class securityConfiguration {
	
	
	private final jwtUtilservice jwtUtilservice = new jwtUtilservice();
	
	
	@SuppressWarnings("removal")
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf().disable().cors().disable()
				.addFilterBefore(new JwtValidationFilter(jwtUtilservice), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/profile/addProfile","/profile/updateProfile","/profile/updateBankAccount","/profile/updateaddress","/profile/getprofile").hasAnyRole("ADMIN","FARMER","DEALER")
                .requestMatchers("/profile/profile/{id}").hasAnyRole("ADMIN")
                .requestMatchers("/profile/swagger-ui/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .build();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	
	

}
