package com.cropdeal.sevice;



import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class jwtService {
	
	
	static final String secret="5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	
	
	
	
	public Claims validateToken(final String token){
		
		Jws<Claims> claimsJws=Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
		
		Claims claims=claimsJws.getBody();
		
		return claims;
	}
	
	
	
	public String genarateToken(int userId,Collection<? extends GrantedAuthority> authorities) {
		GrantedAuthority authority = authorities.iterator().next();
	    String role = authority.getAuthority();
		Map<String, Object> claims=new HashMap<>();
//		System.out.println("hello1"+null);
		return createToken(claims,userId,role);
		
	}
	
	
	private String createToken(Map<String, Object> claims,int userId,String role) {
//		System.out.println("hello"+role);
		claims.put("role", role);
		
//		System.out.println(role+" "+userId);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(""+userId)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
		
	}
	
	private Key getSignKey() {
		
		byte[] keyButes=Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyButes);
		
		
	}
	
	
	

}
