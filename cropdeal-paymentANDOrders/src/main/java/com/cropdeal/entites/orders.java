package com.cropdeal.entites;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class orders {

	@Id
	@Column(length = 100)
	private String orderId;
	private int quantity;
	private int marchentId;
	private String status;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime purchaseTime;
	private String productId;
	@OneToOne()
	@JoinColumn(name = "billId")
	private bills bill;
	
	
	
}
