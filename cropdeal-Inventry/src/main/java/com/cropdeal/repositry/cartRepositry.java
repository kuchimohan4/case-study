package com.cropdeal.repositry;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.cart;
import com.cropdeal.entites.product;
import java.util.List;


@Repository
public interface cartRepositry extends MongoRepository<cart, Integer>{
	
	Optional<cart>  findByProductProductIdAndMarchentId(String productProductId, int marchentId);
	List<cart> findByMarchentId(int marchentId);
	void deleteByMarchentId(int marchentId);
	void deleteByProductProductIdAndMarchentId(String productProductId, int marchentId);

}

