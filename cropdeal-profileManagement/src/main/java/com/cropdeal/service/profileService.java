package com.cropdeal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;
import com.cropdeal.entites.profile;
import com.cropdeal.exception.noProfileFoundException;
import com.cropdeal.repositry.profileRepositry;

import jakarta.validation.Valid;


public interface profileService {
	
	public void addprofile(int userid, profile profile) throws noProfileFoundException;
	
	public profile getprofileById(int id) throws noProfileFoundException ;
	public void updateBankAccount(int id,BankAccounts bAccounts) throws noProfileFoundException ;
	public void updateaddress(int id,address address) throws noProfileFoundException;

	public void updateProfile(int userid, @Valid profile profile) throws noProfileFoundException;
}
