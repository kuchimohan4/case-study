package com.cropdeal.repositry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.profile;

//import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface profileRepositry extends MongoRepository<profile, Integer>{

}
