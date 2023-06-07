package com.cropdeal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.profile;
import com.cropdeal.repositry.profileRepositry;


public interface profileService {
	
	public void addprofile(profile profile);

}
