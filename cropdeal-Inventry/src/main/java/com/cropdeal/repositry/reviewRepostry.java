package com.cropdeal.repositry;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
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
	
	@Aggregation(pipeline = {
		    "{$sort: {reviewId: -1}}",
		    "{$limit: 1}"
		})
	reviews findReviewWithMaxReviewId();

	
	@Aggregation(pipeline = {
		    "{$match: {productId: ?0}}",
		    "{$group: {_id: null, averageRating: {$avg: '$rating'}}}"
		})
	Double getAverageRatingByProductId(String productId);
	@Aggregation(pipeline = {
		    "{$match: {farmerId: ?0}}",
		    "{$group: {_id: null, averageRating: {$avg: '$rating'}}}"
		})
	Double getAverageRatingByFarmerId(int farmerId);

	
}
