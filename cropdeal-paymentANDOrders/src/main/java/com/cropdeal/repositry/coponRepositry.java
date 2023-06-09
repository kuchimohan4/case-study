package com.cropdeal.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.copons;
import com.cropdeal.entites.orders;
import java.util.List;


@Repository
public interface coponRepositry extends JpaRepository<copons, Integer> {

	
	Optional<copons>  findByCoupon(String coupon);
	
	
	}