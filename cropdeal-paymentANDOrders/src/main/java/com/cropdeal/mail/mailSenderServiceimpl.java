package com.cropdeal.mail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.orders;
import com.cropdeal.models.product;

@Service
public class mailSenderServiceimpl implements mailsenderservice {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendOrderPlacedMail(String dealerEmail, orders order, product product) {
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
	
	
	public void sendCartOrderPlacedMail(String dealerEmail, orders order, List<product> products) {
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
	    for (product product : products) {
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
	
	public void sendOrderCancellationMail(String dealerEmail, orders order, List<product> products) {
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
		    for (product product : products) {
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
		
	
	
}
