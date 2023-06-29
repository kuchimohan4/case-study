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
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin("http://localhost:4200")
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
		Map<String, String> resmap = new HashMap<>();
		authService.saveUser(userCredentials);
		resmap.put("msg", "otp sent u");
		return new ResponseEntity<>(resmap ,HttpStatus.OK);
		
	}
	
	
	@PostMapping("/validateMail")
	public ResponseEntity<?> validateMail(@RequestBody Map<String, String> otp) throws InvalidOtpException {
		
		
		Map<String, String> resmap = new HashMap<>();
		resmap.put("msg", authService.validateMail(otp));
		
		return new ResponseEntity<>(resmap ,HttpStatus.OK);

	}
	
	
	@PostMapping("/token")
	public Map<String, String> genToken(@RequestBody userCredentials userCredentials) {
		Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getEmail(), userCredentials.getPassword()));
		if(authentication.isAuthenticated()) {
			int userId=authService.retreveId(userCredentials.getEmail());
			Map<String, String> authmap=new HashMap<>();
			authmap.put("JwtToken", authService.genarateToken(userId,authentication.getAuthorities()) );
			authmap.put("role", authentication.getAuthorities().iterator().next().getAuthority());
			authmap.put("id", ""+userId);
			authmap.put("expirationTime", ""+60*60);
			
		return authmap;
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
