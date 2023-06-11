package com.cropdeal.service;

import java.util.List;
import java.util.Map;

import com.cropdeal.entites.cart;
import com.cropdeal.entites.product;
import com.cropdeal.entites.reviews;
import com.cropdeal.exceptions.noProductFoundException;

import jakarta.validation.Valid;

public interface inventryService {
	
	public product addproduct(int formerId, product product)  throws noProductFoundException;
	public Object updateproduct(@Valid product product) throws noProductFoundException;
	public void deleteproduct(String id, int formerId) throws noProductFoundException;
	public cart addtocart(int merchentId, Map<String, String> inputdata) throws noProductFoundException;
	public product getProductById(String productId) throws noProductFoundException;
	public List<cart> getCartitemsCartsBymarchent(int marchentId);
	public List<product> getallProducts(); 
	public List<product> getallProductsByFarmerId(int farmerId);
	void removefromCart(String productId, int merchentId) throws noProductFoundException;
	void removeAllFromCart(int marchent);
	public reviews addreview(String productId,reviews reviews, int dealerid) throws noProductFoundException;
	public void removereview(String productid, int dealerid) throws noProductFoundException;
	reviews updatereview(String productId, reviews reviews, int dealerid) throws noProductFoundException;
	public String orderPlaced(Map<String, String> orderdetails) throws noProductFoundException;
	public String cartOrderplaced(Map<String, String> orderdetails, int merchentId) throws noProductFoundException;
	public String orderCanceled(Map<String, String> orderdetails) throws noProductFoundException;

}
