package com.cropdeal.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cropdeal.entites.profile;
import com.cropdeal.service.profileService;

import jakarta.validation.Valid;

@RestController 
public class profileController {
	
	
	@Autowired
	private profileService profileService;
	
	
	
	@PostMapping("/addProfile")
	public ResponseEntity<?> addprofile(@Valid @RequestBody profile profile,BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errormap=new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errormap.put(error.getField(), error.getDefaultMessage()));
			
		}
		
		
		
		
		profileService.addprofile(profile);
		
		return "profile added";
		
	}

}
