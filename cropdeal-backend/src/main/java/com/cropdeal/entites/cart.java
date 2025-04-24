package com.cropdeal.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class cart {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	private Integer cartId;
//	private int productId;
	private int quantity;
	private int marchentId;
	private String status;
	private LocalDateTime addedDateTime;
	@OneToOne
	private product product;
	public cart(int quantity, int marchentId, String status, LocalDateTime addedDateTime
			, com.cropdeal.entites.product product
	) {

		super();
		this.quantity = quantity;
		this.marchentId = marchentId;
		this.status = status;
		this.addedDateTime = addedDateTime;
		this.product = product;
	}
	
	
	
}