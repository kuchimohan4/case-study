package com.cropdeal.entites;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Document
public class profile {
	
	@Id
	private int id;
	
	@Min(value = 4)
	private String name;
	@Min(value = 21)
	@Max(value = 70)
	private int age;
	@Min(value = 8)
	private String profilePic;
	@DBRef
	private address address;
	@Min(10)
	@Max(10)
	private double mobileNumber;
	private String bio;
	@DBRef
	private BankAccounts bankAccount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public profile() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public address getAddress() {
		return address;
	}
	public void setAddress(address address) {
		this.address = address;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public BankAccounts getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(BankAccounts bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public double getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(double mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public profile(int id, String name, int age, String profilePic, com.cropdeal.entites.address address,
			double mobileNumber, String bio, BankAccounts bankAccount) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.profilePic = profilePic;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.bio = bio;
		this.bankAccount = bankAccount;
	}
	
	
		
		
	

}
