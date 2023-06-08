package com.cropdeal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cropdeal.entites.cart;
import com.cropdeal.entites.product;
import com.cropdeal.exceptions.noProductFoundException;
import com.cropdeal.service.inventryService;

import jakarta.validation.Valid;

@RestController
public class inventryController {

	@Autowired
	private inventryService inventryService;

	@PostMapping("/addproduct")
	public ResponseEntity<?> addproduct(@Valid @RequestBody product product, BindingResult bindingResult)
			throws noProductFoundException {

		if (bindingResult.hasErrors()) {
			Map<String, String> errormap = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errormap.put(error.getField(), error.getDefaultMessage()));

			return new ResponseEntity<>(errormap, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(inventryService.addproduct(product), HttpStatus.ACCEPTED);
	}

	@PutMapping("/updateproduct")
	public ResponseEntity<?> updateproduct(@Valid @RequestBody product product, BindingResult bindingResult)
			throws noProductFoundException {

		if (bindingResult.hasErrors()) {
			Map<String, String> errormap = new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errormap.put(error.getField(), error.getDefaultMessage()));

			return new ResponseEntity<>(errormap, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(inventryService.updateproduct(product), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteProductByid/{id}")
	public ResponseEntity<HttpStatus> deleteproduct(@PathVariable String id) throws noProductFoundException {

		// hard coding former id later after adding sequrity make sure to get farmer id

		int formerId = 3;

		inventryService.deleteproduct(id, formerId);
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}

	@PostMapping("/addtocart")
	public cart addCart(@RequestBody Map<String, String> inputdata) throws noProductFoundException {
		// hard coding former id later after adding sequrity make sure to get farmer id
		int merchentId = 6;

		return inventryService.addtocart(merchentId, inputdata);
	}

	@GetMapping("/getProductById/{id}")
	public product getProductById(@PathVariable String id) throws noProductFoundException {

		return inventryService.getProductById(id);
	}

	@GetMapping("/getCartItemsByMarchent")
	public List<cart> getCartItemsByMarchent() throws noProductFoundException {
		// hard coding former id later after adding sequrity make sure to get farmer id
		int merchentId = 6;

		return inventryService.getCartitemsCartsBymarchent(merchentId);
	}

	@GetMapping("/getallProducts")
	public List<product> getallProducts() {

		return inventryService.getallProducts();
	}

	@GetMapping("/getAllProductOfFarmer")
	public List<product> getallProductsOfFarmer() {
		// hard coding former id later after adding sequrity make sure to get farmer id

		int formerId = 3;
		return inventryService.getallProductsByFarmerId(formerId);

	}
	@DeleteMapping("/removeFromCart/{productId}")
	public ResponseEntity<HttpStatus> removeFromCart(@PathVariable String productId) throws noProductFoundException{
	
		//hard coding former id later after adding sequrity make sure to get farmer id 
		
		int merchentId = 6;
		
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

}
