package com.mpango.core.auth;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mpango.core.model.MyUser;



@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		logger.debug("CustomAuthenticationProvider->authenticate() >>>>> authentication {} ", authentication);
		
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		if (shouldAuthenticateAgainstThirdPartySystem(name, password)) {

			String URL_LOGIN = "http://localhost:8084/MpangoFarmEngineApplication/api/login/";
			
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
			
			MyUser loginUser = new MyUser(name, name, password);
			
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<MyUser> userDetailsx = restTemplate.postForEntity(URL_LOGIN, loginUser, MyUser.class);
			
			HttpStatus httpresponse = userDetailsx.getStatusCode();
			
			if (httpresponse.equals(HttpStatus.OK)) {
				
				MyUser userDetails = userDetailsx.getBody();
				
				String userRole = userDetails.getUserType();
				
				List<String> roleNames = new ArrayList<String>();
				roleNames.add(userRole);

				List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
				
				if (roleNames != null) {
					for (String role : roleNames) {
						GrantedAuthority authority = new SimpleGrantedAuthority(role);
						grantList.add(authority);
					}
				}				
				usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, grantList);	
				
				logger.debug("CustomAuthenticationProvider->authenticate() >>>>> usernamePasswordAuthenticationToken {} ", usernamePasswordAuthenticationToken);
			}			
			return usernamePasswordAuthenticationToken;
		} else {
			return null;
		}
	}

	private boolean shouldAuthenticateAgainstThirdPartySystem(String name, String password) {
		return true;
	}
}