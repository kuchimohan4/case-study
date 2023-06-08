package com.cropdeal.entites;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;


public class product {


	private int productid;
	private int formerId;
	private String  productName;
	private int avaliablequantity;
	private int initialQuantity;
	private LocalDateTime date;
	private double price;
	private String productDetails;
	private String status;
	private List<String> productImages;
	
	
	
	
	
}
