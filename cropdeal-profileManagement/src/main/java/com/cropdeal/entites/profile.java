package com.cropdeal.entites;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "target", "source" })
@Document
public class profile {
	
	@Id
	private int id;
	
	@Size(min  = 4)
	private String name;
	@Min(value = 21)
	@Max(value = 70)
	private int age;
	@Size(min  = 8)
	private String profilePic;
	@DBRef(lazy = true)
	private address address;
	@Digits(integer = 10, fraction = 0)
	private double mobileNumber;
//	@NotEmpty
	@Email
	private String emailId;
	@Size(min = 15, max = 200)
	private String bio;
	@DBRef(lazy = true)
	private BankAccounts bankAccount;
	@NotEmpty
	@Pattern(regexp = "(DEALER)|(FARMER)")
	private String role;
}
