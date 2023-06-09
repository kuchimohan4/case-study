package com.cropdeal.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class transactionDetails {
	
	private String orderId;
	private String currency;
	private String amount;
	private String key;
	
	
	

}
