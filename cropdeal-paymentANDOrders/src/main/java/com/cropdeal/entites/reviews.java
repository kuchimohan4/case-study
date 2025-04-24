package com.cropdeal.entites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class reviews {
	
	
	@Id
	private int reviewId;
	private Integer productId;
	private int farmerId;
	private int dealearId;
	private int rating;
	private String review;
	private String description;
	private LocalDateTime reviewDate;
	
	
}