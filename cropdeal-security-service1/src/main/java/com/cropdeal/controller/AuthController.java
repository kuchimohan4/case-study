package com.cropdeal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.cropdeal.entity.userCredentials;
import com.cropdeal.exception.EmailAlreadyExistsException;
import com.cropdeal.exception.InvalidOtpException;
import com.cropdeal.sevice.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> addNewUser(@Valid @RequestBody userCredentials userCredentials,BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			Map<String, String> errormap = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errormap.put(error.getField(), error.getDefaultMessage()));

			return new ResponseEntity<>(errormap, HttpStatus.BAD_REQUEST);
		}
	
		return new ResponseEntity<>( authService.saveUser(userCredentials),HttpStatus.ACCEPTED);
		
	}
	
	
	@PostMapping("/validateMail")
	public String validateMail(@RequestBody Map<String, String> otp) throws InvalidOtpException {
		
		return authService.validateMail(otp);
	}
	
	
	@PostMapping("/token")
	public String genToken(@RequestBody userCredentials userCredentials) {
		Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getEmail(), userCredentials.getPassword()));
		System.out.println("hello");
		if(authentication.isAuthenticated()) {
			
			int userId=authService.retreveId(userCredentials.getEmail());
		return authService.genarateToken(userId,authentication.getAuthorities());
		}else {
			throw new RuntimeException("invalid user");
		}
	}
	
	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		authService.validateToken(token);
		return "Token is valid";
	}
	
	
	@ExceptionHandler()
	public ResponseEntity<String> handleemailalredyexist(Exception ex){
		
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	

}
