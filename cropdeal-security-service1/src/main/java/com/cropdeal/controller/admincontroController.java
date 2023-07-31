package com.cropdeal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.cropdeal.entity.userCredentials;
import com.cropdeal.exception.InvalidOtpException;
import com.cropdeal.sevice.AuthService;

import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/adminControl")
public class admincontroController {
	

	@Autowired
	private AuthService authService;
	
	
	@GetMapping("/getFarmers")
	public List<userCredentials> getFarmers(){
		
		return authService.getFarmers();
	}
	
	@GetMapping("/getDelears")
	public List<userCredentials> getDelears(){
		
		return authService.getDelears();
	}
	
	@PutMapping("/changeAccountStatus")
	public ResponseEntity<?> lockUser(@RequestBody userCredentials userdCredent )throws InvalidOtpException{
		
		authService.changeAccountStatus(userdCredent);
		
		return ResponseEntity.status(HttpStatus.OK).body(userdCredent);
	}
	
	

	@ExceptionHandler()
	public ResponseEntity<String> handleemailalredyexist(Exception ex){
		
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
