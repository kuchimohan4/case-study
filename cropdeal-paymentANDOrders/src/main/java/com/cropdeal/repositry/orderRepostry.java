package com.cropdeal.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entites.orders;

@Repository
public interface orderRepostry extends JpaRepository<orders, String> {

}
