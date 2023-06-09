package com.cropdeal.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.cropdeal.entites.bills;
import com.cropdeal.entites.copons;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class product {

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

	private List<reviews> reviews;

}
