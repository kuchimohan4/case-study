package com.cropdeal.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.orders;
import com.cropdeal.entites.transactions;
import com.cropdeal.exception.invalidQuantityException;
import com.cropdeal.exception.noProductFoundException;
import com.cropdeal.models.product;
import com.cropdeal.models.transactionDetails;
import com.cropdeal.repositry.billRepostry;
import com.cropdeal.repositry.coponRepositry;
import com.cropdeal.repositry.inventryServiceProxy;
import com.cropdeal.repositry.orderRepostry;
import com.cropdeal.repositry.transactionRepostry;
import com.razorpay.RazorpayException;

@Service
public class orderserviceImpl implements orderService {
	
	@Autowired
	private billRepostry billRepostry;
	@Autowired
	private coponRepositry coponRepositry;
	@Autowired
	private orderRepostry orderRepostry;
	@Autowired
	private inventryServiceProxy proxy;
	@Autowired
	private paymentService paymentService;
	
	@Autowired
	private transactionRepostry transactionRepostry;
	

	@Override
	public transactionDetails placeOrder(int dealearid, Map<String, String> inputMap) throws noProductFoundException, invalidQuantityException, RazorpayException {
		
		product product= proxy.getProductById(inputMap.get("productId"));
		
		if (product.getAvailableQuantity()<Integer.parseInt(inputMap.get("quantity"))) {
			throw new invalidQuantityException("Invalid quantity please enter quantity less than "+product.getAvailableQuantity());
			
		}
		
		transactionDetails transactionDetail= paymentService.getPayment(Integer.parseInt(inputMap.get("quantity"))*product.getPrice());
		
		transactionRepostry.save(new transactions(transactionDetail.getOrderId(),"pending",product.getProductId(),dealearid,product.getFarmerId(),Integer.parseInt(inputMap.get("quantity")),LocalDateTime.now()));
		
		return transactionDetail;
	}
	
	
	
	

}
