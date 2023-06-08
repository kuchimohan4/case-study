package com.cropdeal.repositry;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.product;
import com.cropdeal.entites.reviews;
import java.util.List;


@Repository
public interface reviewRepostry extends MongoRepository<reviews, Integer>{
	
	Optional<reviews>  findByProductIdAndDealearId(String productId, int dealearId);
	
	void deleteByProductIdAndDealearId(String productId, int dealearId);
	
	@Query(value = "{}", sort = "{reviewId: -1}", fields = "{reviewId: 1}")
    reviews findMaxReviewId();

	
}
