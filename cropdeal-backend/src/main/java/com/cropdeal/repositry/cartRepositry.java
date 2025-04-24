package com.cropdeal.repositry;

import com.cropdeal.entites.cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface cartRepositry extends JpaRepository<cart, Integer> {
//
	Optional<cart> findByProductProductIdAndMarchentId(Integer productProductId, int marchentId);
	Optional<cart>  findByMarchentIdAndProductProductId(int marchentId ,String productProductId );
	List<cart> findByMarchentId(int marchentId);
	void deleteByMarchentId(int marchentId);
	void deleteByProductProductIdAndMarchentId(Integer productProductId, int marchentId);

	

}