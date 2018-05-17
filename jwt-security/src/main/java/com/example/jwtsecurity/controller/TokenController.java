package com.example.jwtsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtsecurity.model.JwtUser;
import com.example.jwtsecurity.security.JwtGenerator;

@RestController
@RequestMapping("/token")
public class TokenController {

	
	private JwtGenerator jwtGenerator;

	public TokenController(JwtGenerator jwtGenerator) {
		//super();
		this.jwtGenerator = jwtGenerator;
	}

	@PostMapping
	public String generate(@RequestBody final JwtUser jwtUser)
	{
		//JwtGenerator jwtGenerator= new JwtGenerator();
		return jwtGenerator.generate(jwtUser);
	}
}
