package com.cropdeal.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class jwtUtilservice {
	
	
	static final String secret="5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	
	
	
	
	public Claims validateToken(final String token){
	
		Jws<Claims> claimsJws=Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
		
		Claims claims=claimsJws.getBody();
		
		return claims;
	}
	
	
	
	private Key getSignKey() {
		
		byte[] keyButes=Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyButes);
		
		
	}
	
	
	

}
