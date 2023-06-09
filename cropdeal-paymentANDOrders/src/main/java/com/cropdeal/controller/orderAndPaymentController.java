package com.cropdeal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cropdeal.entites.orders;
import com.cropdeal.exception.invalidQuantityException;
import com.cropdeal.exception.noProductFoundException;
import com.cropdeal.models.transactionDetails;
import com.cropdeal.service.orderService;
import com.razorpay.RazorpayException;


@RestController
public class orderAndPaymentController {

	@Autowired
	private orderService orderService;
	
	@PostMapping("/placeOrder")
	public transactionDetails placeOrder(@RequestBody Map<String, String> inputMap) throws noProductFoundException, invalidQuantityException, RazorpayException {
		
		int dealearid=6;
		
	    return	orderService.placeOrder(dealearid,inputMap);
		
	}
	
	
	
}
