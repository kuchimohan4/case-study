package com.cropdeal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class userCredentials {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message = "Name is required")
	private String name;
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must have at least 8 characters, one uppercase letter, one lowercase letter, and one digit")
	private String password;
	@Pattern(regexp = "^(FARMER|DEALER)$", message = "Role must be either FARMER or DEALER")
	private String role;
	private Boolean enabled;
	private boolean isAccountNonLocked;
	
	
	
	
	

}
