package com.cropdeal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.cropdeal.entity.userCredentials;
import com.cropdeal.sevice.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/register")
	public String addNewUser(@RequestBody userCredentials userCredentials) {
	
		return authService.saveUser(userCredentials);
		
	}
	
	@PostMapping("/token")
	public String genToken(@RequestBody userCredentials userCredentials) {
		Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getName(), userCredentials.getPassword()));
		if(authentication.isAuthenticated()) {
			String role=authService.retreveRole(userCredentials.getName());
		return authService.genarateToken(userCredentials.getName(),role);
		}else {
			throw new RuntimeException("invalid user");
		}
	}
	
	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		authService.validateToken(token);
		return "Token is valid";
	}
	

}
