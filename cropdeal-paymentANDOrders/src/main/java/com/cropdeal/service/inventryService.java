package com.cropdeal.service;

import com.cropdeal.entites.cart;
import com.cropdeal.entites.product;
import com.cropdeal.entites.reviews;
import com.cropdeal.exception.noProductFoundException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface inventryService {
	
	public product addproduct(int formerId, product product)  throws noProductFoundException;
	public Object updateproduct(@Valid product product, int farmerId) throws noProductFoundException;
	public void deleteproduct(Integer id, int formerId) throws noProductFoundException;
	public cart addtocart(int merchentId, Map<String, String> inputdata) throws noProductFoundException;
	public product getProductById(Integer productId) throws noProductFoundException;
	public List<cart> getCartitemsCartsBymarchent(int marchentId);
	public List<product> getallProducts(); 
	public List<product> getallProductsByFarmerId(int farmerId);
	void removefromCart(Integer productId, int merchentId) throws noProductFoundException;
	void removeAllFromCart(int marchent);
	public reviews addreview(Integer productId,reviews reviews, int dealerid) throws noProductFoundException;
	public void removereview(Integer productid, int dealerid) throws noProductFoundException;
	reviews updatereview(Integer productId, reviews reviews, int dealerid) throws noProductFoundException;
	public String orderPlaced(Map<String, String> orderdetails) throws noProductFoundException;
	public String cartOrderplaced(Map<String, String> orderdetails, int merchentId) throws noProductFoundException;
	public String orderCanceled(Map<String, String> orderdetails) throws noProductFoundException;
	public Double getavgreviewofshop(int id);
	double getavgRatingOfProduct(Integer productId);
	cart reduceProductsFromcart(int merchentId, Map<String, String> inputdata) throws noProductFoundException;
	public boolean isDelearAddedreviewForProduct(int delearId, Integer productId);
	public List<reviews> getReviewsByProductId(Integer productId);
	
}
