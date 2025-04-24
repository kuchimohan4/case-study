package com.cropdeal.repositry;

import com.cropdeal.entites.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface productRepositry extends JpaRepository<product, String> {

	List<product> findByFarmerIdAndProductNameAndIsDeletedFalse(int formerId, String productName);
	List<product> findByFarmerIdAndProductIdAndIsDeletedFalse(int farmerId, Integer productId);

	@Modifying
	@Transactional
	@Query("Update product set isDeleted = True where productId =:productId ")
	void deleteByProductId(Integer productId);
	List<product> findByProductIdAndIsDeletedFalse(Integer productId);
	List<product> findByFarmerIdAndIsDeletedFalse(int farmerId);
	Object findByProductIdAndFarmerIdAndIsDeletedFalse(String anyString, int anyInt);
}