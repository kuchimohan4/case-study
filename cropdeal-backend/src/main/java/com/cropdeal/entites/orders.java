package com.cropdeal.entites;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
	@Id
	private int orderId;
	private List<Integer> quantity;
	private int marchentId;
	private String status;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime purchaseTime;
	private List<String> productIdList; 
	@OneToOne()
	@JoinColumn(name = "billId")
	private bills bill;
	@OneToOne()
	@JoinColumn(name = "transactionId")
	private  transactions transactions;
	
	
	public orders(List<Integer>  quantity, int marchentId, String status, LocalDateTime purchaseTime, List<String> productId, 
			com.cropdeal.entites.transactions transactions) {
		super();
		this.quantity = quantity;
		this.marchentId = marchentId;
		this.status = status;
		this.purchaseTime = purchaseTime;
		this.productIdList = productId;
		this.transactions = transactions;
	}
	
	
	
	
	
	
	
}
