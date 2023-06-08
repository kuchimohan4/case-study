package com.cropdeal.entites;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class order {

	@Id
	private String orderId;
	private int quantity;
	private int marchentId;
	private String status;
	private LocalDateTime purchaseTime;
	private String productId;
	@OneToOne()
	@JoinColumn(name = "billId")
	private bills bill;
	
	
	
	
}
