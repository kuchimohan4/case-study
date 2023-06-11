package com.cropdeal.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;
import com.cropdeal.entites.profile;
import com.cropdeal.exception.noProfileFoundException;
import com.cropdeal.service.profileService;

import jakarta.validation.Valid;


@RestController 
@RequestMapping("/profile")
public class profileController {
	
	
	@Autowired
	private profileService profileServic;
	
	
	
	@PostMapping("/addProfile")
	public ResponseEntity<?> addprofile(@Valid @RequestBody profile profile,BindingResult bindingResult) throws noProfileFoundException {
		
//		userid
		int userid=101;
		if(bindingResult.hasErrors()) {
			Map<String, String> errormap=new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errormap.put(error.getField(), error.getDefaultMessage()));
			
			return new ResponseEntity<>(errormap, HttpStatus.BAD_REQUEST) ;
		}
		profileServic.addprofile(userid ,profile);
		
		return new ResponseEntity<>("profile added",HttpStatus.ACCEPTED);
		
	}
	
	@PutMapping("/updateProfile")
	public ResponseEntity<?> updateProfile(@Valid @RequestBody profile profile,BindingResult bindingResult) throws noProfileFoundException {
		int userid=4;
		if(bindingResult.hasErrors()) {
			Map<String, String> errormap=new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errormap.put(error.getField(), error.getDefaultMessage()));
			
			return new ResponseEntity<>(errormap, HttpStatus.BAD_REQUEST) ;
		}
		profileServic.updateProfile(userid, profile);
		
		return new ResponseEntity<>("profile added",HttpStatus.ACCEPTED);
		
	}
	
	
	@GetMapping("/profile/{id}")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public profile getprofileById(@PathVariable int id) throws noProfileFoundException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = authentication.getName();
		System.out.println(userId);
		
	return profileServic.getprofileById(id);	
	}
	
	@PutMapping("/updateBankAccount")
	public void updateBankAccount(@RequestBody BankAccounts bankAccount) throws noProfileFoundException {
		int userid=4;
		profileServic.updateBankAccount(userid, bankAccount);
	}
	
	@PutMapping("/updateaddress")
	public void updateAddress(@RequestBody address address) throws noProfileFoundException {
		int userid=4;
		profileServic.updateaddress(userid, address);
	}
	
	

}
