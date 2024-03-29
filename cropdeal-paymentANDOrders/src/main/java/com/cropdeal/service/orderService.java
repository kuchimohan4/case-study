package com.cropdeal.service;

import java.util.Map;

import com.cropdeal.entites.orders;
import com.cropdeal.exception.invalidQuantityException;
import com.cropdeal.exception.noProductFoundException;
import com.cropdeal.models.transactionDetails;
import com.razorpay.RazorpayException;

public interface orderService {

	transactionDetails placeOrder(int dealearid, Map<String, String> inputMap) throws noProductFoundException, invalidQuantityException, RazorpayException;

	orders paymentConformation(Map<String, String> paymentdetails) throws noProductFoundException;

	transactionDetails orderCartProducts(int dealearid, Map<String, String> inputMap) throws noProductFoundException, invalidQuantityException, RazorpayException;

	orders cartPaymentConformation(Map<String, String> paymentdetails) throws noProductFoundException;

	orders cancelorder(int orderId, int dealearid) throws noProductFoundException;

}
