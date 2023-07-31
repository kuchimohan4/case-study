package com.cropdeal.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	private static final Logger logger = LoggerFactory.getLogger(reviews.class);
	
	@PostMapping("/addReview/{productId}")
	public reviews addreview(@RequestBody reviews reviews,@PathVariable("productId") String productid) throws noProductFoundException {
		
		int dealerid=requestUserId();
		logger.info("Delear with ID:"+dealerid+" is adding review to product with ID:"+productid);
		return inventryService.addreview(productid, reviews,dealerid);
	}
	
	@DeleteMapping("/removeReview/{productId}")
	public ResponseEntity<HttpStatus> removereview(@PathVariable String productid) throws noProductFoundException {
		
		int dealerid=requestUserId();

		inventryService.removereview(productid,dealerid);
		logger.info("Delear with ID:"+dealerid+" is deleating review to product with ID:"+productid);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updatereview/{productId}")
	public reviews upadtereview(@RequestBody reviews reviews,@PathVariable String productid) throws noProductFoundException {
		
		int dealerid=requestUserId();

		logger.info("Delear with ID:"+dealerid+" is updating review to product with ID:"+productid);
		return  inventryService.updatereview(productid, reviews,dealerid);
	}
	
	@GetMapping("/getReviewsByProductId/{productId}")
	public List<reviews> getReviewsByProductId(@PathVariable String productId){
		
		return inventryService.getReviewsByProductId(productId);
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
	
	
	@GetMapping("/isDelearAddedreviewForProduct/{productId}")
	public ResponseEntity<?> isDelearAddedreview(@PathVariable String productId){
		int delearId=requestUserId();
		
		
		return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("added", inventryService.isDelearAddedreviewForProduct(delearId,productId)));
	}
	
	@GetMapping("/getavgreviewofshop/{id}")
	ResponseEntity<?> getavgreviewofshop(@PathVariable int id){
		
		
		Map<String, String> avgratingmap=new HashMap<>();
		avgratingmap.put("avgrating", ""+inventryService.getavgreviewofshop(id));
		
		return new ResponseEntity<>(avgratingmap, HttpStatus.OK);
	}
	
	@GetMapping("/getavgreviewofProduct/{id}")
	ResponseEntity<?> getavgreviewofProduct(@PathVariable String id){
		
		
		Map<String, String> avgratingmap=new HashMap<>();
		avgratingmap.put("avgrating", ""+inventryService.getavgRatingOfProduct(id));
		
		return new ResponseEntity<>(avgratingmap, HttpStatus.OK);
	}
	

	
}
