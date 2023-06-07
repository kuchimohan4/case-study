package com.cropdeal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.profile;
import com.cropdeal.repositry.profileRepositry;

@Service
public class profileserviceimpl implements profileService {
	
	
	
	@Autowired
	private profileRepositry profileRepositry;
	
	
	public void addprofile(profile profile) {
		
		profileRepositry.save(profile);
		
	}

}
