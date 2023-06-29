package com.cropdeal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cropdeal.entites.copons;
import com.cropdeal.entites.orders;
import com.cropdeal.exception.invalidQuantityException;
import com.cropdeal.exception.noProductFoundException;
import com.cropdeal.models.transactionDetails;
import com.cropdeal.service.orderService;
import com.razorpay.RazorpayException;


@RestController
@RequestMapping("/order")
public class orderAndPaymentController {

	@Autowired
	private orderService orderService;
	
	@PostMapping("/placeOrder")
	public transactionDetails placeOrder(@RequestBody Map<String, String> inputMap) throws noProductFoundException, invalidQuantityException, RazorpayException {
		
//		productId
//		quantity
//		copon
		int dealearid=requestUserId();
		
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
		int dealearid=requestUserId();
		
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
		int dealearid=requestUserId();
		return orderService.cancelorder(orderId,dealearid);
		
	}
	
	@GetMapping("/getCouponByName/{couponCode}")
	public copons getCouponByName(@PathVariable String couponCode) throws noProductFoundException {
		
		return this.orderService.getCouponByName(couponCode);
	}
	
	@GetMapping("/getOrderByOrderId/{orderId}")
	public orders getOrderByOrderId(@PathVariable int orderId) throws noProductFoundException {
		int dealerid=requestUserId();
		return this.orderService.getOrderByOrderId(orderId,dealerid);
	}
	
	
	private int requestUserId() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = authentication.getName();
		return Integer.parseInt(userId);
		
	}
	

	@ExceptionHandler()
	public ResponseEntity<String> handleemailalredyexist(Exception ex){
		
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
}
