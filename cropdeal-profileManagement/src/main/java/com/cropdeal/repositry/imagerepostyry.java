package com.cropdeal.repositry;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.ImageModel;

@Repository
public interface imagerepostyry extends MongoRepository<ImageModel, String> {
	
	Optional<ImageModel> findByName(String name);

}
