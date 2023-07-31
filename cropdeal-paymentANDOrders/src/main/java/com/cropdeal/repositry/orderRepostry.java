package com.cropdeal.repositry;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.orders;

@Repository
public interface orderRepostry extends JpaRepository<orders, Integer> {
	
	Optional<orders>  findByTransactionsTransactionId(String trnsactionId);
	Optional<orders>    findByMarchentIdAndOrderIdAndStatus(int marchentId, int orderId, String status);
	List<orders> findByMarchentIdAndStatus(int marchentId,String status);
	
//	List<orders> findByTransactionsFarmerIdListContaining(int farmerId);
}
