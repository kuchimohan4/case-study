package com.cropdeal.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class mailSenderServiceimpl implements mailsenderservice {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendProfileAddedMail(String toEmail, String firstName) {
	    String subject = "Profile Updated";
	    String body = "Dear " + firstName + " " + ",\n\n"
	            + "We are writing to inform you that your profile has been successfully updated.\n"
	            + "Please review the changes and ensure that all the information is accurate.\n"
	            + "If you have any questions or need further assistance, feel free to contact us.\n\n"
	            + "Thank you for using our services!\n\n"
	            + "Best regards,\n"
	            + "KUCHI's";

	    sendEmail(toEmail, subject, body);
	}
	
	
	@Override
	public void sendotpForregistration(String toEmail, String name,String otp) {
		String subject = "Registration OTP";
	    String body = "Dear " + name + ",\n\n"
	            + "Thank you for registering with our application!\n"
	            + "Please use the following OTP for verification: " + otp + "\n"
	            + "This OTP is valid for the next 5 minutes.\n\n"
	            + "Best regards,\n"
	            + "KUCHI's";

	    sendEmail(toEmail, subject, body);
		
	}
	@Override
	public void sendregistrationSuccessMail(String toEmail, String name) {
	    String subject = "Registration Successful";
	    String body = "Dear " + name + ",\n\n"
	            + "Congratulations! Your registration is successful.\n"
	            + "Thank you for joining our platform.\n\n"
	            + "Best regards,\n"
	            + "KUCHI's";

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
