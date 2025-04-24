package com.cropdeal.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class mailSenderServiceimpl implements mailsenderservice {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendProfileAddedMail(String toEmail, String firstName) throws MessagingException {
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
	public void sendotpForregistration(String toEmail, String name,String otp) throws MessagingException {
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
	public void sendregistrationSuccessMail(String toEmail, String name) throws MessagingException {
	    String subject = "Registration Successful";
	    String body = "Dear " + name + ",\n\n"
	            + "Congratulations! Your registration is successful.\n"
	            + "Thank you for joining our platform.\n\n"
	            + "Best regards,\n"
	            + "KUCHI's";

	    sendEmail(toEmail, subject, body);
	}

	private void sendEmail(String toEmail, String subject, String body) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("kuchimohan4@gmail.com");
		helper.setTo(toEmail);
		helper.setSubject(subject);
		helper.setText(body);
//		FileSystemResource file = new FileSystemResource(new File("C:\\Users\\KUMOHAN\\Downloads\\src.zip"));
//		helper.addAttachment("src.zip", file);



	    javaMailSender.send(message);
	    System.out.println("Mail sent successfully.");
	}

	
		
	
	
}
