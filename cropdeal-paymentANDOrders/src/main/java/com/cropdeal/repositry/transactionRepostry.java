package com.cropdeal.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.orders;
import com.cropdeal.entites.transactions;
@Repository
public interface transactionRepostry extends JpaRepository<transactions, String> {
	
//	 List<orders> findByFarmerIdListContaining(Integer farmerId);

}
