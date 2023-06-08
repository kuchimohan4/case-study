package com.cropdeal.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;
import com.cropdeal.entites.profile;
import com.cropdeal.exception.noProfileFoundException;
import com.cropdeal.service.profileService;

import jakarta.validation.Valid;

@RestController 
public class profileController {
	
	
	@Autowired
	private profileService profileServic;
	
	
	
	@PostMapping("/addProfile")
	public ResponseEntity<?> addprofile(@Valid @RequestBody profile profile,BindingResult bindingResult) throws noProfileFoundException {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errormap=new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errormap.put(error.getField(), error.getDefaultMessage()));
			
			return new ResponseEntity<>(errormap, HttpStatus.BAD_REQUEST) ;
		}
		profileServic.addprofile(profile);
		
		return new ResponseEntity<>("profile added",HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/profile/{id}")
	public profile getprofileById(@PathVariable int id) throws noProfileFoundException {
		
	return profileServic.getprofileById(id);	
	}
	
	@PutMapping("/updateBankAccount/{id}")
	public void updateBankAccount(@PathVariable int id,@RequestBody BankAccounts bankAccount) {
		
		profileServic.updateBankAccount(id, bankAccount);
	}
	
	@PutMapping("/updateaddress/{id}")
	public void updateAddress(@PathVariable int id,@RequestBody address address) {
		
		profileServic.updateaddress(id, address);
	}
	
	

}
