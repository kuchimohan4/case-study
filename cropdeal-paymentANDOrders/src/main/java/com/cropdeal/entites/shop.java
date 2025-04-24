package com.cropdeal.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class shop {
	
	@Id
	private int shopid;
	private String shopName;
	private String shopImg;
	private String shopDesc;
	private String shopRating;

}