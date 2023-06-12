package com.cropdeal.repositry;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cropdeal.exception.noProductFoundException;
import com.cropdeal.models.cart;
import com.cropdeal.models.product;

@FeignClient(name = "CROPDEAL-INVENTRY-SERVICE")
public interface inventryServiceProxy {
	
	@GetMapping("/inventry/getProductById/{id}")
	public product getProductById(@PathVariable String id) throws noProductFoundException;
	
	@PostMapping("/inventry/orderplaced")
	public String orderPlaced(@RequestBody Map<String, String> orderdetails ) throws noProductFoundException;
	
	@GetMapping("/inventry/getCartItemsByMarchentprox")
	public List<cart> getCartItemsByMarchentprox(@RequestParam("merchentId") int merchentId) throws noProductFoundException;
	
	@PostMapping("/inventry/cartOrderplaced")
	public String cartOrderplaced(@RequestBody Map<String, String> orderdetails, int i ) throws noProductFoundException ;
	
	@DeleteMapping("/inventry/orderCanceled")
	public String orderCanceled(@RequestBody Map<String, String> orderdetails ) throws noProductFoundException;

}
