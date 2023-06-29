package com.cropdeal.repositry;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.product;

@Repository
public interface productRepositry extends MongoRepository<product, String>{

	List<product> findByFarmerIdAndProductName(int formerId, String productName);
	List<product> findByFarmerIdAndProductId(int farmerId, String productId);
	List<product> deleteByProductId(String productId);
	List<product> findByProductId(String productId);
	List<product> findByFarmerId(int farmerId);
}
