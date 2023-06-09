package com.cropdeal.entites;

import java.time.LocalDateTime;

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
	private String productId;
	private int dealearId;
	private int farmerId;
	private int quantity;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime transactionTime;
	
	

}
