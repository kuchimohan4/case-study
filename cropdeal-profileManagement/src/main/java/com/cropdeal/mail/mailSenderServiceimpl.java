package com.cropdeal.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;

@Service
public class mailSenderServiceimpl implements mailsenderservice {
	
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendProfileAddedMail(String toEmail, String firstName) {
	    String subject = "Profile Added";
	    String body = "Dear " + firstName + " " + ",\n\n"
	            + "We are writing to inform you that your profile has been successfully added.\n"
	            + "Please review the changes and ensure that all the information is accurate.\n"
	            + "If you have any questions or need further assistance, feel free to contact us.\n\n"
	            + "Thank you for using our services!\n\n"
	            + "Best regards,\n"
	            + "KUCHI'S";

	    sendEmail(toEmail, subject, body);
	}
	
	
	public void sendProfileUpdatedMail(String toEmail, String firstName) {
	    String subject = "Profile Updated";
	    String body = "Dear " + firstName + " " + ",\n\n"
	            + "We are writing to inform you that your profile has been successfully Updated.\n"
	            + "Please review the changes and ensure that all the information is accurate.\n"
	            + "If you have any questions or need further assistance, feel free to contact us.\n\n"
	            + "Thank you for using our services!\n\n"
	            + "Best regards,\n"
	            + "KUCHI'S";

	    sendEmail(toEmail, subject, body);
	}
	
	public void sendBankAccountUpdatedMail(String toEmail, String firstName, BankAccounts bAccounts) {
	    String subject = "Bank Account Updated";
	    String body = "Dear " + firstName + ",\n\n"
	            + "We are writing to inform you that your bank account information has been updated.\n"
	            + "Please review the changes below:\n\n"
	            + "Account Holder Name: " + bAccounts.getAccountHolderName() + "\n"
	            + "Account Number: " + bAccounts.getAccountNumber() + "\n"
	            + "IFSC Code: " + bAccounts.getIfscCode() + "\n\n"
	            + "If you have any questions or need further assistance, feel free to contact us.\n\n"
	            + "Thank you for using our services!\n\n"
	            + "Best regards,\n"
	            + "KUCHI'S";

	    sendEmail(toEmail, subject, body);
	}
	
	public void sendAddressUpdatedMail(String toEmail, String firstName, address address) {
	    String subject = "Address Updated";
	    String body = "Dear " + firstName + ",\n\n"
	            + "We are writing to inform you that your address has been updated.\n"
	            + "Please review the changes below:\n\n"
	            + "Address Line 1: " + address.getAddressLine1() + "\n"
	            + "Address Line 2: " + address.getAddressLine2() + "\n"
	            + "City: " + address.getCity() + "\n"
	            + "State: " + address.getState() + "\n"
	            + "Pin Code: " + address.getPinCode() + "\n\n"
	            + "If you have any questions or need further assistance, feel free to contact us.\n\n"
	            + "Thank you for using our services!\n\n"
	            + "Best regards,\n"
	            + "KUCHI'S";

	    sendEmail(toEmail, subject, body);
	}

	
	private void sendEmail(String toEmail, String subject, String body) {
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setFrom("kuchimohan4@gmail.com");
	    message.setTo(toEmail);
	    message.setSubject(subject);
	    message.setText(body);
	    javaMailSender.send(message);
	    System.out.println("Mail sent successfully.");
	}
		
	
	
}
