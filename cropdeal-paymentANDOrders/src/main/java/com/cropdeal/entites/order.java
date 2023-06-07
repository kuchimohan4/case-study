package com.cropdeal.entites;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class order {

	@Id
	private int orderId;
	private int quantity;
	private int marchentId;
	private String status;
	private LocalDateTime purchaseTime;
	private product product;
	@DBRef
	private bills bill;
	public order() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
	public LocalDateTime getPurchaseTime() {
		return purchaseTime;
	}
	public void setPurchaseTime(LocalDateTime purchaseTime) {
		this.purchaseTime = purchaseTime;
	}
	public product getProduct() {
		return product;
	}
	public void setProduct(product product) {
		this.product = product;
	}
	public bills getBill() {
		return bill;
	}
	public void setBill(bills bill) {
		this.bill = bill;
	}
	public order(int orderId, int quantity, int marchentId, String status, LocalDateTime purchaseTime,
			com.cropdeal.entites.product product, bills bill) {
		super();
		this.orderId = orderId;
		this.quantity = quantity;
		this.marchentId = marchentId;
		this.status = status;
		this.purchaseTime = purchaseTime;
		this.product = product;
		this.bill = bill;
	}
	
	
	
	
}
