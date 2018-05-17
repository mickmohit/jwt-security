package com.example.jwtsecurity.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.jwtsecurity.model.JwtAuthenticationToken;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

	

	public JwtAuthenticationTokenFilter() {
		super("/rest/**");
		// TODO Auto-generated constructor stub
	}

	private AuthenticationManager authenticationManager;
	private JwtSuccessHandler authenticationSuccessHandler;

	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest arg0, HttpServletResponse arg1)
			throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub
		String header=
		arg0.getHeader("Authorization");
		
		if(header==null || !header.startsWith("Token "))
		{
			throw new RuntimeException("Jwt Token is missing");
		}
		
		String authenticationToken = header.substring(6);
		
		JwtAuthenticationToken token= new JwtAuthenticationToken(authenticationToken);
		
		
		return getAuthenticationManager().authenticate(token);
	}



	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}
	
	
	/*public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		// TODO Auto-generated method stub
		this.authenticationManager=authenticationManager;
	}

	public void setAutheticationSuccessHandler(JwtSuccessHandler authenticationSuccessHandler) {
		// TODO Auto-generated method stub
		this.authenticationSuccessHandler=authenticationSuccessHandler;
	}*/

}
