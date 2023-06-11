package com.cropdeal.mail;

import com.cropdeal.entites.product;

public interface mailsenderservice {

	
	public void sendProductAddedMail(String toEmail, product product);
	public void sendProductUpdatedMail(String toEmail, product product);
	public void sendProductRemovedMail(String toEmail, String productName);
}
