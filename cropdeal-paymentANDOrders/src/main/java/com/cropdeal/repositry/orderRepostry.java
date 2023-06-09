package com.cropdeal.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.orders;

import jakarta.validation.constraints.AssertFalse.List;

@Repository
public interface orderRepostry extends JpaRepository<orders, Integer> {
	
	Optional<orders>  findByTransactionsTransactionId(String trnsactionId);
	Optional<orders>    findByMarchentIdAndOrderIdAndStatus(int marchentId, int orderId, String status);

}
