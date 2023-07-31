package com.cropdeal.entites;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({ "target", "source" })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class reviews {
	
	
	@Id
	private int reviewId;
	private String productId;
	private int farmerId;
	private int dealearId;
	private int rating;
	private String review;
	private String description;
	private LocalDateTime reviewDate;
	
	
}
