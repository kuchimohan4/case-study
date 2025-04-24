package com.cropdeal.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
//import jakarta.validation.constraints.Digits;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "target", "source" })
@Entity
public class address {
	
	@Id
	private int id;
//	@NotEmpty
//    @Size(max = 100)
    private String addressLine1;

//    @Size(max = 100)
    private String addressLine2;

//    @NotEmpty
//    @Size(max = 50)
    private String city;

//    @NotEmpty
//    @Size(max = 50)
    private String state;

//    @NotEmpty
//    @Digits(integer = 6, fraction = 0)
    private int pinCode;
	
	
	
	
}
