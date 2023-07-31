package com.cropdeal.controller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cropdeal.entites.reviews;
import com.cropdeal.entites.shop;
import com.cropdeal.exceptions.noshopfoundexception;
import com.cropdeal.service.shopservice;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/shop")
public class shopController {
	
	@Autowired
	private shopservice shopservice;
	private static final Logger logger = LoggerFactory.getLogger(shopController.class);
	
	
	@PostMapping("/addShop")
	public shop newshop(@RequestBody shop shop) throws noshopfoundexception {
		
		logger.info("farmer with id:"+requestUserId()+"is adding new shop");
		return shopservice.newshop(shop,requestUserId());
		
	}
	
	
	
	@PutMapping("/updateShop")
	public shop updateshop(@RequestBody shop shop) throws noshopfoundexception {
		
		logger.info("farmer with id:"+requestUserId()+"is updating  shop");
		return shopservice.updateShop(shop,requestUserId());
		
	}
	
	@GetMapping("/allShops")
	public java.util.List<shop> getallshops(){
		
		
		return shopservice.gettallshops();
		
	}
	
	
	
	@GetMapping("/shopById/{id}")
	public shop getShopById(@PathVariable("id") int id) throws noshopfoundexception{
		
		
		return shopservice.getShopById(id);
		
	}
	
	@GetMapping("/doesFarmerHaveShop/{farmerId}")
	ResponseEntity<?> doesFarmerHaveShop(@PathVariable int farmerId){
		
		
		return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("haveAShop", shopservice.doesFarmerHaveShop(farmerId)));
	}
	
	@GetMapping("/doesFarmerHaveShop")
	ResponseEntity<?> doesFarmerHaveSho(){
		
		int farmerId=requestUserId();
		return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("haveAShop", shopservice.doesFarmerHaveShop(farmerId)));
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
