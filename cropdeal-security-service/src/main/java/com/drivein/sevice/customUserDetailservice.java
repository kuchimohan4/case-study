package com.drivein.sevice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.drivein.config.customUserDetails;
import com.drivein.entity.userCredentials;
import com.drivein.repository.userCreantialsRepositry;
@Service
public class customUserDetailservice implements UserDetailsService {
	
	@Autowired
	private userCreantialsRepositry userCreantialsRepositry;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<userCredentials> credentials=userCreantialsRepositry.findByName(username);
		return credentials.map(customUserDetails::new).orElseThrow( () -> new UsernameNotFoundException("no user found with username"+username));
	}

}
