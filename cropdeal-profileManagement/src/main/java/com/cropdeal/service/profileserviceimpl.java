package com.cropdeal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;
import com.cropdeal.entites.profile;
import com.cropdeal.exception.noProfileFoundException;
import com.cropdeal.repositry.BankAccountRepostry;
import com.cropdeal.repositry.addressRepositry;
import com.cropdeal.repositry.profileRepositry;

@Service
public class profileserviceimpl implements profileService {
	
	
	
	@Autowired
	private profileRepositry profileRepositry;
	
	@Autowired
	private BankAccountRepostry bankAccountRepostry;
	
	@Autowired
	private addressRepositry addressRepositry;
	
	@Autowired
	private mailsenderservice mailsenderservice;
	
//	@EventListener(ApplicationReadyEvent.class)
	public void addprofile(profile profile) throws noProfileFoundException {
		
		Optional<profile> prOptional= profileRepositry.findById(profile.getId());
		if (prOptional.isPresent()) {
			throw new noProfileFoundException("u have already added the profile");
			
		}
		addressRepositry.save(profile.getAddress());
		bankAccountRepostry.save(profile.getBankAccount());
		profileRepositry.save(profile);
		mailsenderservice.sendProfileAddedMail(profile.getEmailId(), profile.getName());
		
	}
	
	public profile getprofileById(int id) throws noProfileFoundException {
		
		
		return profileRepositry.findById(id).orElseThrow(()->new noProfileFoundException("no profile found with id"+id));
	}
	
	
	public void updateBankAccount(int id,BankAccounts bAccounts) {
		
		bAccounts.setId(id);
		bankAccountRepostry.save(bAccounts);
		
	}
	
	public void updateaddress(int id,address address) {
		
		address.setId(id);
		addressRepositry.save(address);
		
	}

}
