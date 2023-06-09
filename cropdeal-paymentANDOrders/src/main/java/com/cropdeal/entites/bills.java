package com.cropdeal.entites;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class bills {
	
	
		@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	    @GenericGenerator(name = "native",strategy = "native")
		@Id
		private int billId;
		private double totalAmount;
		private double payableAmount;
		private String paymentMode;
		@JoinColumn(name = "couponId")
		@ManyToOne(optional = true)
		private copons coupon;
		private double discountAmount;
		
		
}

