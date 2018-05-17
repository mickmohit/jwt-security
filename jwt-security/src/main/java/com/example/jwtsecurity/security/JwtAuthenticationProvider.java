package com.example.jwtsecurity.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.jwtsecurity.model.JwtAuthenticationToken;
import com.example.jwtsecurity.model.JwtUser;
import com.example.jwtsecurity.model.JwtUserDetails;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private JwtValidator validator;
	
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authenticationPasswordToken)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)authenticationPasswordToken;
		String token =jwtAuthenticationToken.getToken();
		JwtUser jwtUser= validator.validate(token);
		 
		if(jwtUser==null)
		{
			throw new RuntimeException("JWT Token is incorrect");
		}
		
		List<GrantedAuthority> grantedAuthorities=
				AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());
		
	return	new JwtUserDetails(jwtUser.getUserName(),jwtUser.getId(),token,grantedAuthorities);
		
		//return null;
	}

}
