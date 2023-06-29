package com.cropdeal.entites;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class shop {
	
	@Id
	private int shopid;
	private String shopName;
	private String shopImg;
	private String shopDesc;
	private String shopRating;

}
