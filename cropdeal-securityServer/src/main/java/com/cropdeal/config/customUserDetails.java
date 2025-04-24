package com.cropdeal.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cropdeal.entity.userCredentials;

public class customUserDetails implements UserDetails{

	
	private String email;
	private String password;
//	private Collection<? extends GrantedAuthority> authorities;
	private String role;
	private boolean enabled;
	private boolean isAccountNonLocked;
	
	
	

	public customUserDetails(String email, String password, String role, boolean enabled, boolean isAccountNonLocked) {
		this.email = email;
		this.password = password;
		this.role = role ;// Convert the role to a GrantedAuthority
		this.enabled = enabled;
		this.isAccountNonLocked = isAccountNonLocked;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

}
