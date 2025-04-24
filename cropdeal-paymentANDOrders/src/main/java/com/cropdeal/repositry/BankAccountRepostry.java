package com.cropdeal.repositry;

import com.cropdeal.entites.BankAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BankAccountRepostry extends JpaRepository<BankAccounts, Integer> {

}
