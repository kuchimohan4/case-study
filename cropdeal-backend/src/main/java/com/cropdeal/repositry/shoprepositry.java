package com.cropdeal.repositry;

import com.cropdeal.entites.shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface shoprepositry extends JpaRepository<shop, Integer> {

	
}
