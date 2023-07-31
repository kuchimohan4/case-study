package com.cropdeal.controller;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cropdeal.entites.cart;
import com.cropdeal.entites.product;
import com.cropdeal.exceptions.noProductFoundException;
import com.cropdeal.service.inventryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventry")
public class inventryController {
	private static final Logger logger = LoggerFactory.getLogger(inventryController.class);
	@Autowired
	private inventryService inventryService;

	@PostMapping("/addproduct")
	public ResponseEntity<?> addproduct(@Valid @RequestBody product product, BindingResult bindingResult)
			throws noProductFoundException {
		int formerId = requestUserId();
		if (bindingResult.hasErrors()) {
			Map<String, String> errormap = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errormap.put(error.getField(), error.getDefaultMessage()));
			logger.error("Farmer with Id:"+formerId+" is failed to add  product");
			return new ResponseEntity<>(errormap, HttpStatus.BAD_REQUEST);
		}
		logger.info("Farmer with Id:"+formerId+" is adding an product");
		return new ResponseEntity<>(inventryService.addproduct(formerId,product), HttpStatus.OK);
	}

	@PutMapping("/updateproduct")
	public ResponseEntity<?> updateproduct(@Valid @RequestBody product product, BindingResult bindingResult)
			throws noProductFoundException {

		if (bindingResult.hasErrors()) {
			Map<String, String> errormap = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errormap.put(error.getField(), error.getDefaultMessage()));

			return new ResponseEntity<>(errormap, HttpStatus.BAD_REQUEST);
		}
		int farmerId=requestUserId();
		logger.info("Farmer with Id:"+farmerId+" is updating an product");
		return new ResponseEntity<>(inventryService.updateproduct(product,farmerId), HttpStatus.OK);
	}

	@DeleteMapping("/deleteProductByid/{id}")
	public ResponseEntity<HttpStatus> deleteproduct(@PathVariable String id) throws noProductFoundException {

		// hard coding former id later after adding sequrity make sure to get farmer id

		int formerId = requestUserId();

		inventryService.deleteproduct(id, formerId);
		logger.info("Farmer with Id:"+formerId+" is deleting an product");
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}

	@PostMapping("/addtocart")
	public cart addCart(@RequestBody Map<String, String> inputdata) throws noProductFoundException {
		// hard coding former id later after adding sequrity make sure to get farmer id
		int merchentId = requestUserId();

		return inventryService.addtocart(merchentId, inputdata);
	}
	
	@PutMapping("/reduceFromCart")
	public cart reduceProductsFromcart(@RequestBody Map<String, String> inputdata) throws noProductFoundException {
		
		int merchentId = requestUserId();

		return inventryService.reduceProductsFromcart(merchentId, inputdata);
		
	}
	
	

	@GetMapping("/getProductById/{id}")
	public product getProductById(@PathVariable String id) throws noProductFoundException {

		return inventryService.getProductById(id);
	}

	@GetMapping("/getCartItemsByMarchent")
	public List<cart> getCartItemsByMarchent() throws noProductFoundException {
		// hard coding former id later after adding sequrity make sure to get farmer id
		int merchentId = requestUserId();

		return inventryService.getCartitemsCartsBymarchent(merchentId);
	}
	
	@GetMapping("/getCartItemsByMarchentprox")
	public List<cart> getCartItemsByMarchentprox(@RequestParam("merchentId") int merchentId) throws noProductFoundException {
		// hard coding former id later after adding sequrity make sure to get farmer id
//		int merchentId = 6;

		return inventryService.getCartitemsCartsBymarchent(merchentId);
	}

	@GetMapping("/getallProducts")
	public List<product> getallProducts() {

		return inventryService.getallProducts();
	}

	@GetMapping("/getAllProductOfFarmer/{id}")
	public List<product> getallProductsOfFarmer(@PathVariable int id) {
		// hard coding former id later after adding sequrity make sure to get farmer id

//		int formerId = requestUserId();
		return inventryService.getallProductsByFarmerId(id);

	}
	@DeleteMapping("/removeFromCart/{productId}")
	public ResponseEntity<HttpStatus> removeFromCart(@PathVariable String productId) throws noProductFoundException{
	
		//hard coding former id later after adding sequrity make sure to get farmer id 
		
		int merchentId = requestUserId();
		
		inventryService.removefromCart(productId, merchentId);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}
	@DeleteMapping("/removeAllFromCart")
	public ResponseEntity<HttpStatus> removeAllFromCart() throws noProductFoundException{
	
		//hard coding former id later after adding sequrity make sure to get farmer id 
		
		int merchentId = 6;
		
		inventryService.removeAllFromCart( merchentId);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/orderplaced")
	public String orderPlaced(@RequestBody Map<String, String> orderdetails ) throws noProductFoundException {
//		System.out.println("hello");
		logger.info("product with ID:"+orderdetails.get("productId")+" is ordered");
		return inventryService.orderPlaced(orderdetails);
		
	}
	
	@PostMapping("/cartOrderplaced")
	public String cartOrderplaced(@RequestBody Map<String, String> orderdetails ) throws noProductFoundException {
//		System.out.println("hello");
		int merchentId=Integer.parseInt(orderdetails.get("marchentId"));
		orderdetails.remove("marchentId");
		return inventryService.cartOrderplaced(orderdetails,merchentId);
		
	}
	
	@DeleteMapping("/orderCanceled")
	public String orderCanceled(@RequestBody Map<String, String> orderdetails ) throws noProductFoundException {
//		System.out.println("hello");
		logger.info("product with ID:"+orderdetails.get("productId")+"has been canceled");
		return inventryService.orderCanceled(orderdetails);
		
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
