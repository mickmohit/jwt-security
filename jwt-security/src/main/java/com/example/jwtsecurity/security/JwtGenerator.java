package com.example.jwtsecurity.security;

import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.example.jwtsecurity.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.lang.Strings;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Component
public class JwtGenerator  {
	
	private Key key;
	
	public String generate(JwtUser jwtUser) {
		// TODO Auto-generated method stub
		Claims claim= Jwts.claims() 
				.setSubject(jwtUser.getUserName());
			claim.put("userId", String.valueOf(jwtUser.getId()));
			claim.put("role", jwtUser.getRole());
				
			String secret = "youtube";
			/*			
			String temp = new String(Base64.getEncoder().encode(key.getEncoded()));
			
			byte[] keyBytes = secret.getEncoded();

			String base64Encoded = TextCodec.BASE64.encode(keyBytes);
			
			byte[] decodedKey = Base64.getDecoder().decode(secret);
			// rebuild key using SecretKeySpec
			SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
			
			final ApiKey apiKey = this . internalDataStore . getApiKey ();
			byte [] secret1 = apiKey . getSecret (). getBytes ( Strings . UTF_8 ); */
			
			byte[] bytesEncoded = Base64.getEncoder().encode(secret.getBytes());
			String encoded = Base64.getEncoder().encodeToString(bytesEncoded);

			//String temp = new String(Base64.getEncoder().encode(Key.getEncoded()));
			
			SecretKey secretKey = null;
			try {
				secretKey = KeyGenerator.getInstance("AES").generateKey();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// get base64 encoded version of the key
			String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
			
			SecretKey key = MacProvider.generateKey();
			byte[] keyBytes = key.getEncoded();

			String base64Encoded = TextCodec.BASE64.encode(keyBytes);
			
		return	Jwts.builder().setClaims(claim).signWith(SignatureAlgorithm.HS512, "youtube").compact();
				//With(SignatureAlgorithm.ES512, bytesEncoded).compact();
				//signWith(SignatureAlgorithm.ES512,"YouTube").compact();
			
	}

}
