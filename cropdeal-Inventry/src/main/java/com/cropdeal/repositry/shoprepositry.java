package com.cropdeal.repositry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.shop;

@Repository
public interface shoprepositry extends MongoRepository<shop, Integer> {

	
}
