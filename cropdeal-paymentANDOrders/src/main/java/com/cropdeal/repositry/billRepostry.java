package com.cropdeal.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.bills;

@Repository
public interface billRepostry extends JpaRepository<bills, Integer> {

}
