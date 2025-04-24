package com.cropdeal.mail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;
import com.cropdeal.entites.product;
import com.cropdeal.util.ZipUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.orders;
import com.cropdeal.models.productdto;

@Service
public class mailSenderServiceimpl implements mailsenderservice {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendOrderPlacedMail(String dealerEmail, orders order, productdto product) {
		int orderId = order.getOrderId();
		int quantity=order.getQuantity().get(0);
		double totalAmount = order.getBill().getTotalAmount();
		double payableAmount = order.getBill().getPayableAmount();
		String paymentMode = order.getBill().getPaymentMode();
		double discountAmount = order.getBill().getDiscountAmount();
		String coupon = order.getBill().getCoupon().getCoupon();

		// Construct the email body
		String subject = "Order Placed";
		String body = "Dear Dealer,\n\n"
				+ "Your order with ID: " + orderId + " has been successfully placed.\n\n"
				+ "Order Details:\n"
				+ "Total Amount: $" + totalAmount + "\n"
				+ "Payable Amount: $" + payableAmount + "\n"
				+ "Payment Mode: " + paymentMode + "\n"
				+ "Discount Amount: $" + discountAmount + "\n\n"
				+ "Coupon Used: " + coupon + "\n\n"
				+ "Product Details:\n"
				+ "Product ID: " + product.getProductId() + "\n"
				+ "Product Name: " + product.getProductName() + "\n"
				+ "Quantity: " + quantity + "\n"
				+ "Price: $" + product.getPrice() + "\n"
				// Add any other product details you want to include
				+ "\n"
				+ "Thank you for your order. We will process it soon.\n\n"
				+ "Best regards,\n"
				+ "KUCHI'S";

		sendEmail(dealerEmail, subject, body);
	}


	public void sendCartOrderPlacedMail(String dealerEmail, orders order, List<productdto> products) {
		int orderId = order.getOrderId();
		double totalAmount = order.getBill().getTotalAmount();
		double payableAmount = order.getBill().getPayableAmount();
		String paymentMode = order.getBill().getPaymentMode();
		double discountAmount = order.getBill().getDiscountAmount();
		String coupon = order.getBill().getCoupon().getCoupon();

		// Construct the email body
		String body = "Dear Dealer,\n\n"
				+ "Your order with ID: " + orderId + " has been successfully placed.\n\n"
				+ "Order Details:\n"
				+ "Total Amount: $" + totalAmount + "\n"
				+ "Payable Amount: $" + payableAmount + "\n"
				+ "Payment Mode: " + paymentMode + "\n"
				+ "Discount Amount: $" + discountAmount + "\n\n"
				+ "Coupon Used: " + coupon + "\n\n"
				+ "Product Details:\n";
//	            + "<table>"
//	            + "<tr><th>Product ID</th><th>Product Name</th><th>Quantity</th><th>Price</th></tr>";
//	    int i=0;
//	    for (product product : products) {
//	        body += "<tr>"
//	                + "<td>" + product.getProductId() + "</td>"
//	                + "<td>" + product.getProductName() + "</td>"
//	                + "<td>" + order.getQuantity().get(i) + "</td>"
//	                + "<td>$" + product.getPrice() + "</td>"
//	                + "</tr>";
//	        i++;
//	    }
		int i=0;
		for (productdto product : products) {
			body += "Product ID: " + product.getProductId() + "\n"
					+ "Product Name: " + product.getProductName() + "\n"
					+ "Quantity: " + order.getQuantity().get(i) + "\n"
					+ "Price: $" + product.getPrice() + "\n\n";
			i++;
		}

		body +=  "Thank you for your order. We will process it soon.\n\n"
				+ "Best regards,\n"
				+ "KUCHI'S";

		String subject = "Order Placed";
		sendEmail(dealerEmail, subject, body);
	}

	public void sendOrderCancellationMail(String dealerEmail, orders order, List<productdto> products) {
		int orderId = order.getOrderId();
		double totalAmount = order.getBill().getTotalAmount();
		double payableAmount = order.getBill().getPayableAmount();
		String paymentMode = order.getBill().getPaymentMode();
		double discountAmount = order.getBill().getDiscountAmount();
		String coupon = order.getBill().getCoupon().getCoupon();

		String subject = "Order Cancellation";
		String body = "Dear Dealer,\n\n"
				+ "Your order with ID: " + orderId + " has been canceled.\n\n"
				+ "Order Details:\n"
				+ "Total Amount: $" + totalAmount + "\n"
				+ "Payable Amount: $" + payableAmount + "\n"
				+ "Payment Mode: " + paymentMode + "\n"
				+ "Discount Amount: $" + discountAmount + "\n\n"
				+ "Coupon Used: " + coupon + "\n\n"
				+ "Product Details:\n";

		int i=0;
		for (productdto product : products) {
			body += "Product ID: " + product.getProductId() + "\n"
					+ "Product Name: " + product.getProductName() + "\n"
					+ "Quantity: " + order.getQuantity().get(i) + "\n"
					+ "Price: $" + product.getPrice() + "\n\n";
			i++;
		}
		body += "We apologize for any inconvenience caused.\n\n"
				+ "If you have any questions or need further assistance, feel free to contact us.\n\n"
				+ "Best regards,\n"
				+ "KUCHI'S";

		sendEmail(dealerEmail, subject, body);


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


	@Async
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


	@Async
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


	public void sprjsmail(String toEmail, String prjPath, Boolean forZIpAndMail) throws MessagingException, IOException {
		if (forZIpAndMail){
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("kuchimohan4@gmail.com");
			helper.setTo(toEmail);
			helper.setSubject("Done bro");
			helper.setText("body");
			Path sourceFolderPath = Paths.get(prjPath+"\\src");
			Path zipPath = Paths.get(prjPath+"\\output.zip");
			ZipUtils.zipFolder(sourceFolderPath, zipPath);
//				return "Folder zipped successfully!";

			FileSystemResource file = new FileSystemResource(new File(prjPath+"\\output.zip"));
			helper.addAttachment("src.zip", file);

			javaMailSender.send(message);
			System.out.println("Mail sent successfully.");

		}else {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("kuchimohan4@gmail.com");
			helper.setTo(toEmail);
			helper.setSubject("Done bro");
			helper.setText("body");
			FileSystemResource file = new FileSystemResource(new File(prjPath+"\\src.zip"));
			helper.addAttachment("src.zip", file);



			javaMailSender.send(message);
			System.out.println("Mail sent successfully.");

		}

	}



}
