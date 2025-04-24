package com.cropdeal.service;

import com.cropdeal.models.transactionDetails;
import com.razorpay.RazorpayException;

public interface paymentService {
	
	
	transactionDetails getPayment(double amount) throws RazorpayException;

}
