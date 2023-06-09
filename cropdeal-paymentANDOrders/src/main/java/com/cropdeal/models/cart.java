package com.cropdeal.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class cart {


	private int quantity;
	private int marchentId;
	private String status;
	private LocalDateTime addedDateTime;
	private product product;
	
	
}
