package com.cropdeal.repositry;

import com.cropdeal.entites.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface imagerepostyry extends JpaRepository<ImageModel, Integer> {

	@Query("select img from ImageModel img where name=:name order by uploadTime desc limit 1" )
	Optional<ImageModel> findByName(String name);

}
