package com.cropdeal.repositry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.address;

@Repository
public interface addressRepositry extends MongoRepository<address, Integer>{

}
