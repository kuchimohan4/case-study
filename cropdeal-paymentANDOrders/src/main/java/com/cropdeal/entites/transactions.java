package com.cropdeal.entites;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class transactions {
	
	@jakarta.persistence.Id
	@Column(length = 100)
	private String transactionId;
	private String status;
	private int dealearId;
	private List<Integer> farmerIdList;
	private double amount;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime transactionTime;
	private String razorpay_order_id;
	private String razorpay_payment_id;
	private String razorpay_signature;
	public transactions(String transactionId, String status, int dealearId, List<Integer> farmerId, double amount,
			LocalDateTime transactionTime) {
		super();
		this.transactionId = transactionId;
		this.status = status;
		this.dealearId = dealearId;
		this.farmerIdList = farmerId;
		this.amount = amount;
		this.transactionTime = transactionTime;
	}
	
	
	
	
}
