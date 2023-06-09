package com.cropdeal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		
//		productId
//		quantity
//		copon
		int dealearid=6;
		
	    return	orderService.placeOrder(dealearid,inputMap);
		
	}
	
	@PostMapping("/transactionconformation")
	public orders paymentConformation(@RequestBody Map<String, String> paymentdetails) throws noProductFoundException {
		
//		orderid
//		razorpay_payment_id
//		razorpay_order_id
//		razorpay_signature
		
		return orderService.paymentConformation(paymentdetails);
		
	}
	
	@PostMapping("/orderCartProducts")
	public transactionDetails orderCartProducts(@RequestBody Map<String, String> inputMap) throws noProductFoundException, invalidQuantityException, RazorpayException {
//		copon
		int dealearid=6;
		
	    return	orderService.orderCartProducts(dealearid,inputMap);
		
	}
	
	
	@PostMapping("/cartPaymentConformation")
	public orders paymentCartConformation(@RequestBody Map<String, String> paymentdetails) throws noProductFoundException {
		
//		orderid
//		razorpay_payment_id
//		razorpay_order_id
//		razorpay_signature
		
		return orderService.cartPaymentConformation(paymentdetails);
		
	}
	
	@DeleteMapping("/cancelorder/{orderId}")
	public orders cancelorder(@PathVariable int orderId) throws noProductFoundException {
		int dealearid=6;
		return orderService.cancelorder(orderId,dealearid);
		
	}
	
	
}
