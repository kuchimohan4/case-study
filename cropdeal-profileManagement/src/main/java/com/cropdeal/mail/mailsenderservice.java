package com.cropdeal.mail;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;

public interface mailsenderservice {

	
	public void sendProfileAddedMail(String toEmail, String name);
	public void sendProfileUpdatedMail(String toEmail, String name);
	public void sendBankAccountUpdatedMail(String toEmail, String firstName, BankAccounts bAccounts) ;
	public void sendAddressUpdatedMail(String toEmail, String firstName, address address);
}
