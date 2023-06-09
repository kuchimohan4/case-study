package com.cropdeal.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.cropdeal.models.transactionDetails;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import netscape.javascript.JSObject;


@Service
public class paymentServiceimpl implements paymentService {
	
	private static final String SECRET_ID = "rzp_test_snvXEPmPJNCZ3U";
	private static final String SECRET_KEY = "xSexxVJlCuQxC4L0DcvT9LAX";
	private static final String CURRENCY="INR";
	@Override
	public transactionDetails getPayment(double amount) throws RazorpayException {
		
		JSONObject jsObject=new JSONObject();
		
		jsObject.put("amount", amount);
		jsObject.put("currency", CURRENCY);
		RazorpayClient razorpayClient=new RazorpayClient(SECRET_ID,SECRET_KEY);
		
		Order order=razorpayClient.orders.create(jsObject);
		
		
		return genrateTransactionDetails(order);
	}
	
	
	public transactionDetails genrateTransactionDetails(Order order) {
		String orderid=order.get("id");
		String currency=order.get("currency");
		String amount=order.get("amount").toString();
		return new transactionDetails(orderid,currency,amount,SECRET_KEY);
	}
	

}
