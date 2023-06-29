package com.cropdeal.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;
import com.cropdeal.entites.profile;
import com.cropdeal.exception.noProfileFoundException;
import com.cropdeal.mail.mailsenderservice;
import com.cropdeal.rabbitmq.rabbitmqEmitter;
import com.cropdeal.repositry.BankAccountRepostry;
import com.cropdeal.repositry.addressRepositry;
import com.cropdeal.repositry.profileRepositry;

import jakarta.validation.Valid;

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
	
	@Autowired
	private rabbitmqEmitter rabbitmqEmitter;
	
//	@EventListener(ApplicationReadyEvent.class)
	public void addprofile(int userid, profile profile) throws noProfileFoundException {
		
		Optional<profile> prOptional= profileRepositry.findById(userid);
		if (prOptional.isPresent()) {
			throw new noProfileFoundException("u have already added the profile");
			
		}
		profile.setId(userid);
		address address=profile.getAddress();
		address.setId(userid);
		addressRepositry.save(address);
		BankAccounts bankAccounts=profile.getBankAccount();
		bankAccounts.setId(userid);
		bankAccountRepostry.save(bankAccounts);
		profileRepositry.save(profile);
		
		Map<String, String> emmitmap=new HashMap<>();
		emmitmap.put("type", "addedProfile");
		emmitmap.put("email", profile.getEmailId());
		emmitmap.put("name", profile.getName());
		
		
		rabbitmqEmitter.emmitmsg(emmitmap);
		
//		mailsenderservice.sendProfileAddedMail(profile.getEmailId(), profile.getName());
		
		
		
	}
	
	@Override
	public void updateProfile(int userid, @Valid profile profile) throws noProfileFoundException {
		// TODO Auto-generated method stub
		Optional<profile> prOptional= profileRepositry.findById(userid);
		if (!prOptional.isPresent()) {
			throw new noProfileFoundException("u have not added profile to update");
			
		}
		profile.setId(userid);
		profile.setAddress(prOptional.get().getAddress());
		profile.setBankAccount(prOptional.get().getBankAccount());
		profileRepositry.save(profile);
		
		Map<String, String> emmitmap=new HashMap<>();
		emmitmap.put("type", "updatedProfile");
		emmitmap.put("email", profile.getEmailId());
		emmitmap.put("name", profile.getName());
		
		
		rabbitmqEmitter.emmitmsg(emmitmap);
		
	}
	
	public profile getprofileById(int id) throws noProfileFoundException {
		
		
		return profileRepositry.findById(id).orElseThrow(()->new noProfileFoundException("no profile found with id"+id));
	}
	
	
	public void updateBankAccount(int id,BankAccounts bAccounts) throws noProfileFoundException {
		
		profile profile= profileRepositry.findById(id).orElseThrow(()-> new noProfileFoundException("no profile found to update bank accout"));
		
		bAccounts.setId(id);
		bankAccountRepostry.save(bAccounts);
		
		Map<String, String> emmitmap=new HashMap<>();
		emmitmap.put("type", "BankAccountUpdated");
		emmitmap.put("email", profile.getEmailId());
		emmitmap.put("name", profile.getName());
		emmitmap.put("accountHolderName", bAccounts.getAccountHolderName());
		emmitmap.put("accountNumber", bAccounts.getAccountNumber());
		emmitmap.put("ifscCode", bAccounts.getIfscCode());
		
		rabbitmqEmitter.emmitmsg(emmitmap);
		
	}
	
	public void updateaddress(int id,address address)  throws noProfileFoundException{
		
		profile profile= profileRepositry.findById(id).orElseThrow(()-> new noProfileFoundException("no profile found to update bank accout"));
		
		
		address.setId(id);
		addressRepositry.save(address);
		
		Map<String, String> emmitmap=new HashMap<>();
		emmitmap.put("type", "addressChanged");
		emmitmap.put("email", profile.getEmailId());
		emmitmap.put("name", profile.getName());
		emmitmap.put("addressLine1", address.getAddressLine1());
		emmitmap.put("addressLine2", address.getAddressLine2());
		emmitmap.put("city", address.getCity());
		emmitmap.put("state",address.getState());
		emmitmap.put("pinCode", ""+address.getPinCode());
		
		rabbitmqEmitter.emmitmsg(emmitmap);
		
	}

	

}
