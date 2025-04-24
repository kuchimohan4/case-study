package com.cropdeal.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class product {



		@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
		@GenericGenerator(name = "native",strategy = "native")
	 	@Id
	    private Integer productId;
	    
//	    @NotNull
	    private int farmerId;
	    
//	    @NotBlank
//	    @Size(max = 100)
	    private String productName;
	    
//	    @Min(1)
//	    @Positive
	    private int availableQuantity;
	    
	    @JsonIgnore
//	    @Positive
	    private int initialQuantity;
	    
//	    @NotNull
	    private LocalDateTime date;
	    
//	    @DecimalMin("0.0")
	    private double price;
	    
//	    @NotBlank
	    private String productDetails;
	    
//	    @NotBlank
	    private String status;
	    
	    private List<String> productImages;
	    
	    @JsonIgnore
//	    @DBRef(lazy = true)
		@OneToMany
	    private List<reviews> reviews;

		private  boolean isDeleted;
	
	
//	    public void genrateProductId() {
//
//			this.productId =
//		}
	
}