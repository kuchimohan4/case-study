package com.cropdeal.entites;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class product {

	@Id
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
	
	public product() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public int getFormerId() {
		return formerId;
	}
	public void setFormerId(int formerId) {
		this.formerId = formerId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getAvaliablequantity() {
		return avaliablequantity;
	}
	public void setAvaliablequantity(int avaliablequantity) {
		this.avaliablequantity = avaliablequantity;
	}
	public int getInitialQuantity() {
		return initialQuantity;
	}
	public void setInitialQuantity(int initialQuantity) {
		this.initialQuantity = initialQuantity;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<String> productImages) {
		this.productImages = productImages;
	}

	public product(int productid, int formerId, String productName, int avaliablequantity, int initialQuantity,
			LocalDateTime date, double price, String productDetails, String status, List<String> productImages) {
		super();
		this.productid = productid;
		this.formerId = formerId;
		this.productName = productName;
		this.avaliablequantity = avaliablequantity;
		this.initialQuantity = initialQuantity;
		this.date = date;
		this.price = price;
		this.productDetails = productDetails;
		this.status = status;
		this.productImages = productImages;
	}
	
	
	
	
	
	
}
