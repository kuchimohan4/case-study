package com.cropdeal.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Pattern;
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
public class BankAccounts {
	@Id
	private int id;
//	@NotEmpty
//    @Size(max = 100)
    private String accountHolderName;

//    @NotEmpty
//    @Size(max = 18,min = 8)
    private String accountNumber;

//    @NotEmpty
//    @Pattern(regexp = "[A-Z]{4}[0-9]{7}")
    private String ifscCode;
}
