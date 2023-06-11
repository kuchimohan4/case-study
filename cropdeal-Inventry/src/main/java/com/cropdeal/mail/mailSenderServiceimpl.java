package com.cropdeal.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.product;

@Service
public class mailSenderServiceimpl implements mailsenderservice {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendProductAddedMail(String toEmail, product product) {
	    String subject = "Product Added";
	    String body = "Dear Farmer,\n\n"
	            + "Your product has been successfully added to our site.\n"
	            + "Product Details:\n\n"
	            + "Product ID: " + product.getProductId() + "\n"
	            + "Product Name: " + product.getProductName() + "\n"
	            + "Available Quantity: " + product.getAvailableQuantity() + "\n"
	            + "Price: " + product.getPrice() + "\n"
	            + "Product Details: " + product.getProductDetails() + "\n\n"
	            + "Your product is now available for sale on our site. Customers can view and purchase it.\n\n"
	            + "Thank you for choosing our platform!\n\n"
	            + "Best regards,\n"
	            + "KUCHI'S";

	    sendEmail(toEmail, subject, body);
	}
	
	public void sendProductUpdatedMail(String toEmail, product product) {
	    String subject = "Product Updated";
	    String body = "Dear Farmer,\n\n"
	            + "Your product has been successfully updated on our site.\n"
	            + "Product Details:\n\n"
	            + "Product ID: " + product.getProductId() + "\n"
	            + "Product Name: " + product.getProductName() + "\n"
	            + "Available Quantity: " + product.getAvailableQuantity() + "\n"
	            + "Price: " + product.getPrice() + "\n"
	            + "Product Details: " + product.getProductDetails() + "\n\n"
	            + "Please note that the product details have been updated.\n\n"
	            + "Thank you for choosing our platform!\n\n"
	            + "Best regards,\n"
	            + "KUCHI'S";

	    sendEmail(toEmail, subject, body);
	}
	
	public void sendProductRemovedMail(String toEmail, String productName) {
	    String subject = "Product Removed";
	    String body = "Dear Farmer,\n\n"
	            + "We regret to inform you that your product has been removed from our site.\n"
	            + "Product Name: " + productName + "\n\n"
	            + "If you have any questions or concerns, please feel free to contact us.\n\n"
	            + "Thank you for your cooperation!\n\n"
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
