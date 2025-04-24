package com.cropdeal.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class otpManager {
	
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
	@Id
	private int id;
	private int otp;
	private String genratedFor;
	private String genratedForReason;
	private LocalDateTime genratedAt;
	private LocalDateTime expiresAt;
	public otpManager(int otp, String genratedFor, String genratedForReason, LocalDateTime genratedAt,
			LocalDateTime expiresAt) {
		super();
		this.otp = otp;
		this.genratedFor = genratedFor;
		this.genratedForReason = genratedForReason;
		this.genratedAt = genratedAt;
		this.expiresAt = expiresAt;
	}
	

}
