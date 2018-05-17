package com.example.jwtsecurity.security;

import org.springframework.stereotype.Component;

import com.example.jwtsecurity.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator {

	private  static final String secret = "youtube";
	
	public JwtUser validate(String token) {
		// TODO Auto-generated method stub
		  
		JwtUser jwtUser=null;
		try {
		Claims claim= Jwts.parser().
				setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
		
		 jwtUser = new JwtUser();
		jwtUser.setUserName(claim.getSubject());
		jwtUser.setId(Long.parseLong((String)claim.get("userId")));
		jwtUser.setRole((String) claim.get("role"));
		}catch(Exception e) {}
		
		return jwtUser;
	}

}
