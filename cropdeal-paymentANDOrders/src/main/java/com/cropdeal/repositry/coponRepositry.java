package com.cropdeal.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.copons;
import com.cropdeal.entites.orders;

@Repository
public interface coponRepositry extends JpaRepository<copons, Integer> {

	
	
	
	
	}