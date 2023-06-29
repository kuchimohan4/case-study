package com.cropdeal.entites;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({ "target", "source" })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class product {

	 	@Id
	    private String productId;
	    
	    @NotNull
	    private int farmerId;
	    
	    @NotBlank
	    @Size(max = 100)
	    private String productName;
	    
	    @Min(1)
	    @Positive
	    private int availableQuantity;
	    
	    @JsonIgnore
//	    @Positive
	    private int initialQuantity;
	    
	    @NotNull
	    private LocalDateTime date;
	    
	    @DecimalMin("0.0")
	    private double price;
	    
	    @NotBlank
	    private String productDetails;
	    
//	    @NotBlank
	    private String status;
	    
	    private List<String> productImages;
	    
	    @JsonIgnore
	    @DBRef(lazy = true)
	    private List<reviews> reviews;
	
	
	    public void genrateProductId() {
			
			this.productId=UUID.randomUUID().toString().split("-",5)[0];
		}
	
}
