package com.cropdeal.repositry;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cropdeal.exception.noProductFoundException;
import com.cropdeal.models.product;

@FeignClient(name = "CROPDEAL-INVENTRY-SERVICE")
public interface inventryServiceProxy {
	
	@GetMapping("/inventry/getProductById/{id}")
	public product getProductById(@PathVariable String id) throws noProductFoundException;

}
