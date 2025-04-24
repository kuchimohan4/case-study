package com.cropdeal.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
//import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "target", "source" })
@Entity
public class profile {
	
	@Id
	private int id;
	
//	@Size(min  = 4)
	private String name;
//	@Min(value = 21)
//	@Max(value = 70)
	private int age;
//	@Size(min  = 8)
	private String profilePic;
//	@DBRef(lazy = true)
	@OneToOne
	private address address;
//	@Digits(integer = 10, fraction = 0)
	private double mobileNumber;
//	@NotEmpty
//	@Email
	private String emailId;
//	@Size(min = 15, max = 200)
	private String bio;
//	@DBRef(lazy = true)
	@OneToOne
	private BankAccounts bankAccount;
//	@NotEmpty
//	@Pattern(regexp = "(DEALER)|(FARMER)")
	private String role;
}
