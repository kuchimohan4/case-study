package com.cropdeal.repositry;

import com.cropdeal.entites.profile;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface profileRepositry extends JpaRepository<profile, Integer> {


    @Query("Select p.emailId from profile p where p.id=:id")
    String findEmailIdById(Integer id);

}
