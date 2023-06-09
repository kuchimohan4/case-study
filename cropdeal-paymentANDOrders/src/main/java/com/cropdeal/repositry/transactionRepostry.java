package com.cropdeal.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.transactions;
@Repository
public interface transactionRepostry extends JpaRepository<transactions, String> {

}
