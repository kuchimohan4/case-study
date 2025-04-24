package com.cropdeal.repositry;

import com.cropdeal.entites.address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface addressRepositry extends JpaRepository<address, Integer> {

}
