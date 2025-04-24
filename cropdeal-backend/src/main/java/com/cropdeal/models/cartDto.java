package com.cropdeal.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class cartDto {


	private int quantity;
	private int marchentId;
	private String status;
	private LocalDateTime addedDateTime;
	private productdto product;
	
	
}
