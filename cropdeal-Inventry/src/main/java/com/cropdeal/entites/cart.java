package com.cropdeal.entites;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class cart {

	@Id
	private int cartId;
//	private int productId;
	private int quantity;
	private int marchentId;
	private String status;
	private LocalDateTime addedDateTime;
	@DBRef
	private product product;
	public cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getMarchentId() {
		return marchentId;
	}
	public void setMarchentId(int marchentId) {
		this.marchentId = marchentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public product getProduct() {
		return product;
	}
	public void setProduct(product product) {
		this.product = product;
	}
	public LocalDateTime getAddedDateTime() {
		return addedDateTime;
	}
	public void setAddedDateTime(LocalDateTime addedDateTime) {
		this.addedDateTime = addedDateTime;
	}
	public cart(int cartId, int quantity, int marchentId, String status, LocalDateTime addedDateTime,
			com.cropdeal.entites.product product) {
		super();
		this.cartId = cartId;
		this.quantity = quantity;
		this.marchentId = marchentId;
		this.status = status;
		this.addedDateTime = addedDateTime;
		this.product = product;
	}
	
	
	
	
	
}
