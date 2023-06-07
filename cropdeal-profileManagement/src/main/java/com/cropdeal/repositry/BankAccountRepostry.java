package com.cropdeal.repositry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.BankAccounts;
@Repository
public interface BankAccountRepostry extends MongoRepository<BankAccounts, Integer> {

}
