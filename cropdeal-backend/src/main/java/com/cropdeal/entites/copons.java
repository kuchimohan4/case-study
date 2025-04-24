package com.cropdeal.entites;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class copons {
	
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
	@Id
	private int couponId;
	private String coupon;
	private int couponDiscount;
	private int maxLimit;
	@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startDate;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime enddate;
	
	

}
