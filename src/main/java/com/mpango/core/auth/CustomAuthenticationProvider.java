package com.mpango.core.auth;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
			ResponseEntity<MyUser> userDetailsResponse = restTemplate.postForEntity(URL_LOGIN, loginUser, MyUser.class);
			HttpStatus httpresponse = userDetailsResponse.getStatusCode();

			if (httpresponse.equals(HttpStatus.OK)) {
				MyUser userDetailsObj = userDetailsResponse.getBody();
				ObjectMapper mapper = new ObjectMapper();
				String jsonInString;
				try {
					jsonInString = mapper.writeValueAsString(userDetailsObj);
					JSONObject jsonObject = new JSONObject(jsonInString);
					String userRole = jsonObject.getString("userType");
					List<String> roleNames = new ArrayList<String>();
					roleNames.add(userRole);

					List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

					if (roleNames != null) {
						for (String role : roleNames) {
							GrantedAuthority authority = new SimpleGrantedAuthority(role);
							grantList.add(authority);
						}
					}
					usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetailsObj,
							password, grantList);

				} catch (JsonProcessingException e) {
					// e.printStackTrace();
					logger.debug("CustomAuthenticationProvider->authenticate() >>>>> JsonProcessingException {} ", e);
				} catch (JSONException e) {
					// e.printStackTrace();
					logger.debug("CustomAuthenticationProvider->authenticate() >>>>> JSONException {} ", e);
				}
			}
			return usernamePasswordAuthenticationToken;
		} else {
			return null;
		}
	}

	// @Override
	public Authentication authenticatexxxx(Authentication authentication) throws AuthenticationException {

		logger.debug("CustomAuthenticationProvider->authenticate() >>>>> authentication {} ", authentication);

		String name = authentication.getName();
		String password = authentication.getCredentials().toString();

		if (shouldAuthenticateAgainstThirdPartySystem(name, password)) {

			String URL_LOGIN = "http://localhost:8084/MpangoFarmEngineApplication/api/login/";

			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;

			MyUser loginUser = new MyUser(name, name, password);

			RestTemplate restTemplate = new RestTemplate();

			// ResponseEntity<MyUser> userDetailsx = restTemplate.postForEntity(URL_LOGIN,
			// loginUser, MyUser.class);

			ResponseEntity<Object> userDetailsx = restTemplate.postForEntity(URL_LOGIN, loginUser, Object.class);

			HttpStatus httpresponse = userDetailsx.getStatusCode();

			if (httpresponse.equals(HttpStatus.OK)) {

				// Authentication userDetails = (Authentication)userDetailsx.getBody();

				// UserDetails userDetails = (UserDetails) userDetailsx.getBody();

				Object userDetails = userDetailsx.getBody();
				logger.debug("CustomAuthenticationProvider->authenticate() >>>>> userDetails {} ", userDetails);

				ObjectMapper mapper = new ObjectMapper();
				// UserDetails user = new UserDetails();

				String jsonInString;
				try {
					jsonInString = mapper.writeValueAsString(userDetails);

					JSONObject jsonObject = new JSONObject(jsonInString);
					JSONArray newJSON = jsonObject.getJSONArray("authorities");

					String jsonPersonData = newJSON.get(0).toString();

					JSONObject jsonObjectAuth = new JSONObject(jsonPersonData);
					String userRole = jsonObjectAuth.getString("authority");

					List<String> roleNames = new ArrayList<String>();
					roleNames.add(userRole);

					List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

					if (roleNames != null) {
						for (String role : roleNames) {
							GrantedAuthority authority = new SimpleGrantedAuthority(role);
							grantList.add(authority);
						}
					}
					usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password,
							grantList);

				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					logger.debug("CustomAuthenticationProvider->authenticate() >>>>> JsonProcessingException {} ", e);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					logger.debug("CustomAuthenticationProvider->authenticate() >>>>> JSONException {} ", e);

				}

				// System.out.println(newJSON.toString());

				// System.out.println(jsonObject.getString("authority"));
				// System.out.println(jsonObject.getJSONArray("argv"));

				// UserDetails ud = (UserDetails) userDetails;

				// if( userDetails instanceof UserDetails) {
				// UserDetails user = (UserDetails) userDetails;

				// MyUser userDetailsxx = (MyUser) userDetailsx.getBody();

				// usernamePasswordAuthenticationToken = new
				// UsernamePasswordAuthenticationToken(userDetails, password, null);
				// }

				// String userRole = userDetails.getUserType();

				// List<String> roleNames = new ArrayList<String>();
				// roleNames.add(userRole);

				// List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

				// if (roleNames != null) {
				// for (String role : roleNames) {
				// GrantedAuthority authority = new SimpleGrantedAuthority(role);
				// grantList.add(authority);
				// }
				// }
				// usernamePasswordAuthenticationToken = new
				// UsernamePasswordAuthenticationToken(userDetails, password, grantList);
				// usernamePasswordAuthenticationToken = new
				// UsernamePasswordAuthenticationToken(userDetails, password);

				logger.debug(
						"CustomAuthenticationProvider->authenticate() >>>>> usernamePasswordAuthenticationToken {} ",
						usernamePasswordAuthenticationToken);
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