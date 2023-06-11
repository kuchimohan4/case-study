package com.cropdeal.sevice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cropdeal.config.customUserDetails;
import com.cropdeal.entity.userCredentials;
import com.cropdeal.repository.userCreantialsRepositry;
@Service
public class customUserDetailservice implements UserDetailsService {
	
	@Autowired
	private userCreantialsRepositry userCreantialsRepositry;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<userCredentials> credentials=userCreantialsRepositry.findByEmail(email);
		return credentials.map(cred -> new customUserDetails(
				cred.getEmail(),
				cred.getPassword(),
				cred.getRole(),
				cred.getEnabled(),
				cred.isAccountNonLocked()
		)).orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
		}

}
