package com.cropdeal.entites;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
public class cart {

	@Id
	private String cartId;
//	private int productId;
	private int quantity;
	private int marchentId;
	private String status;
	private LocalDateTime addedDateTime;
	@DBRef(lazy = true)
	private product product;
	public cart(int quantity, int marchentId, String status, LocalDateTime addedDateTime,
			com.cropdeal.entites.product product) {
		super();
		this.quantity = quantity;
		this.marchentId = marchentId;
		this.status = status;
		this.addedDateTime = addedDateTime;
		this.product = product;
	}
	
	
	
}
