package com.cropdeal.sevice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cropdeal.entity.userCredentials;
import com.cropdeal.repository.userCreantialsRepositry;

@Service
public class AuthService {
	
	@Autowired
	private userCreantialsRepositry userCreantialsRepositry;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private jwtService jwtService;
	
	public String saveUser(userCredentials userCredentials) {
		userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
		userCredentials.setRole("user");
		userCreantialsRepositry.save(userCredentials);
		System.out.println(userCredentials.getRole());
		return "user added sucessfully";
		
	}
	
	
	public String genarateToken(String userName,String role) {
		
//		System.out.println("hello1"+role);
		return jwtService.genarateToken(userName,role);
		
		
	}
	
	public void validateToken(String token) {
		
		jwtService.validateToken(token);
	}


	public String retreveRole(String name) {
		
		Optional<userCredentials> usercred=userCreantialsRepositry.findByName(name);
		userCredentials userCredential=usercred.orElse(null);
		
		return userCredential.getRole();
	}
	
	

}
