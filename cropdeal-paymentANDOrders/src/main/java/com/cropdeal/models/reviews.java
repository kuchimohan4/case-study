package com.cropdeal.models;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class reviews {
	

	private int reviewId;
	private String productId;
	private int dealearId;
	private int rating;
	private String description;
	private LocalDateTime reviewDate;
	
	
}
