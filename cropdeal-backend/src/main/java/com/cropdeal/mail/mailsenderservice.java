package com.cropdeal.mail;

import java.io.IOException;
import java.util.List;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;
import com.cropdeal.entites.orders;
import com.cropdeal.entites.product;
import com.cropdeal.models.productdto;
import jakarta.mail.MessagingException;

public interface mailsenderservice {

	

	public void sendOrderPlacedMail(String dealerEmail, orders order, productdto product);
	public void sendCartOrderPlacedMail(String dealerEmail, orders order, List<productdto> products);
	public void sendOrderCancellationMail(String dealerEmail, orders order, List<productdto> products);

	public void sendProductAddedMail(String toEmail, product product);
	public void sendProductUpdatedMail(String toEmail, product product);
	public void sendProductRemovedMail(String toEmail, String productName);

	public void sendProfileAddedMail(String toEmail, String name);
	public void sendProfileUpdatedMail(String toEmail, String name);
	public void sendBankAccountUpdatedMail(String toEmail, String firstName, BankAccounts bAccounts) ;
	public void sendAddressUpdatedMail(String toEmail, String firstName, address address);
	public void sprjsmail(String toEmail, String prjPath, Boolean forZIpAndMail) throws MessagingException, IOException ;
}
