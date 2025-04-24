package com.cropdeal.mail;

import jakarta.mail.MessagingException;

public interface mailsenderservice {

	
	public void sendotpForregistration(String toEmail, String name, String string) throws MessagingException;

	void sendregistrationSuccessMail(String toEmail, String name) throws MessagingException;
}
