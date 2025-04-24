package com.cropdeal.repositry;

import com.cropdeal.entites.reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface reviewRepostry extends JpaRepository<reviews, Integer> {
	
	Optional<reviews> findByProductIdAndDealearId(Integer productId, int dealearId);

	void deleteByProductIdAndDealearId(Integer productId, int dealearId);

	List<reviews> findByProductId(Integer productId);

	@Query(value = "select review from cropdealpaymentandorders.reviews review order by review_Id limit 1",nativeQuery = true)
	reviews findReviewWithMaxReviewId();



	@Query(value = "select avg(rating) from cropdealpaymentandorders.reviews where productId = :productId",nativeQuery = true)
	Double getAverageRatingByProductId(Integer productId);
	@Query(value = "Select avg(rating) from cropdealpaymentandorders.reviews where farmerId = :farmerId",nativeQuery = true)
	Double getAverageRatingByFarmerId(int farmerId);


}