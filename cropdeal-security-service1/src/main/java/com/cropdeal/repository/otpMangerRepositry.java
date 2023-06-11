package com.cropdeal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entity.otpManager;

@Repository
public interface otpMangerRepositry extends JpaRepository<otpManager, Integer> {

	Optional<otpManager> findByGenratedForAndOtpAndGenratedForReason( String genratedFor,int otp,String genratedForReason);
	   void  deleteByGenratedFor(String genratedFor);
	
}
