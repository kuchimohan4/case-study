package com.cropdeal.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class productdto {

	private String productId;

	private int farmerId;

	private String productName;

	private int availableQuantity;

	private int initialQuantity;

	private LocalDateTime date;

	private double price;

	private String productDetails;

	private String status;

	private List<String> productImages;

	private List<reviewsDto> reviews;

}
