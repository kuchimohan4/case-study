package com.cropdeal.repositry;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.cart;
import com.cropdeal.entites.product;
import java.util.List;


@Repository
public interface cartRepositry extends MongoRepository<cart, String>{
	
	Optional<cart>  findByProductProductIdAndMarchentId(String productProductId, int marchentId);
	Optional<cart>  findByMarchentIdAndProductProductId(int marchentId ,String productProductId );
	List<cart> findByMarchentId(int marchentId);
	void deleteByMarchentId(int marchentId);
	void deleteByProductProductIdAndMarchentId(String productProductId, int marchentId);
	

}

