package com.cropdeal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cropdeal.entites.reviews;
import com.cropdeal.exceptions.noProductFoundException;
import com.cropdeal.service.inventryService;

@RestController
@RequestMapping("/review")
public class riviewController {

	@Autowired
	private inventryService inventryService;
	
	
	@PostMapping("/addReview/{productId}")
	public reviews addreview(@RequestBody reviews reviews,@PathVariable("productId") String productid) throws noProductFoundException {
		
		int dealerid=requestUserId();
		
		return inventryService.addreview(productid, reviews,dealerid);
	}
	
	@DeleteMapping("/removeReview/{productId}")
	public ResponseEntity<HttpStatus> removereview(@PathVariable String productid) throws noProductFoundException {
		
		int dealerid=requestUserId();

		inventryService.removereview(productid,dealerid);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updatereview/{productId}")
	public reviews upadtereview(@RequestBody reviews reviews,@PathVariable String productid) throws noProductFoundException {
		
		int dealerid=requestUserId();

		
		return  inventryService.updatereview(productid, reviews,dealerid);
	}
	
	private int requestUserId() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = authentication.getName();
		return Integer.parseInt(userId);
		
	}
	
}
