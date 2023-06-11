package com.cropdeal.mail;

public interface mailsenderservice {

	
	public void sendotpForregistration(String toEmail, String name, String string);

	void sendregistrationSuccessMail(String toEmail, String name);
}
