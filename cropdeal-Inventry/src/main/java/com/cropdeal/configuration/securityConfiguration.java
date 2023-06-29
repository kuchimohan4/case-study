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

import static org.springframework.security.config.Customizer.withDefaults;
import com.cropdeal.util.jwtUtilservice;


@Configuration
@EnableWebSecurity
public class securityConfiguration {
	
	
	private final jwtUtilservice jwtUtilservice = new jwtUtilservice();
	
	
	@SuppressWarnings("removal")
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		
		return http.csrf().disable()
                .addFilterBefore(new JwtValidationFilter(jwtUtilservice), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/inventry/addproduct", "/inventry/updateproduct", "/inventry/deleteProductByid/{id}", "/shop/addShop", "/shop/updateShop").hasAnyRole("ADMIN", "FARMER")
                .requestMatchers("/inventry/getCartItemsByMarchent", "/inventry/addtocart", "/inventry//removeFromCart/{productId}", "/inventry/removeAllFromCart").hasAnyRole("ADMIN", "DEALER")
                .requestMatchers("/profile/swagger-ui/**", "/inventry/getProductById/{id}", "/inventry/getallProducts", "/inventry/getAllProductOfFarmer/{id}", "/shop/allshops", "/shop/shopById/{id}").permitAll()
                .anyRequest().permitAll()
                .and()
                .build();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	
	

}
