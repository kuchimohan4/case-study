package com.cropdeal.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cropdeal.sevice.jwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class JwtValidationFilter extends OncePerRequestFilter {
	
	private Claims claims;
	
	
	private jwtService jwtServiceutil;
	
	public JwtValidationFilter(jwtService jwtUtilservice) {
		this.jwtServiceutil=jwtUtilservice;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = extractJwtToken(request);
		if (jwtToken != null) {

			if (jwtTokenIsValid(jwtToken)) {
//				System.out.println("hello");
//				request.setAttribute("userName", username);
				
//				System.out.println(claims.getSubject()+" "+claims.get("role"));
				
				String userid=claims.getSubject();
				String role=claims.get("role").toString();
				Authentication authentication=new UsernamePasswordAuthenticationToken(userid, null,AuthorityUtils.createAuthorityList("ROLE_"+role));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				filterChain.doFilter(request, response);
		    }
			else {
				
				filterChain.doFilter(request, response);
			}
		}
		else {

			filterChain.doFilter(request, response);
			
		}
		
	 
	    
		
		
	}
	private String extractJwtToken(HttpServletRequest request) {
	    String header = request.getHeader("Authorization");
	    if (header != null && header.startsWith("Bearer ")) {
	        return header.substring(7);
	    }
	    return null;
	}
	 
	private boolean jwtTokenIsValid(String jwtToken) {
		try {
//			System.out.println(jwtToken);
	       this.claims= jwtServiceutil.validateToken(jwtToken);
//	        System.out.println(jwtToken);
	        return true;
	    } catch (Exception ex) {
	        return false;
	    }
	}

}