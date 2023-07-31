package com.cropdeal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cropdeal.entity.userCredentials;
import java.util.List;

@Repository
public interface userCreantialsRepositry extends JpaRepository<userCredentials, Integer> {

	Optional<userCredentials> findByName(String username);
	Optional<userCredentials> findByEmail(String email);
	List<userCredentials> findByRoleAndEnabled(String role, Boolean enabled);

}