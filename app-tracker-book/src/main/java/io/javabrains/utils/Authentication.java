package io.javabrains.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.javabrains.model.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Authentication  implements AuthenticationService{

	@Override
	public User getUser(HttpServletRequest request) {
		
		String token= request.getHeader(HttpHeaders.AUTHORIZATION) != "" && request.getHeader(HttpHeaders.AUTHORIZATION) != null ? request.getHeader(HttpHeaders.AUTHORIZATION) : "";
		User user = null;
		try {
			user = WebClient.create() 
					.get() 
					.uri("http://localhost:8081/username")
					.header(org.springframework.http.HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
					.header(HttpHeaders.AUTHORIZATION, token)
					.retrieve()
					.bodyToMono(User.class)
					.block();
			
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return user;
	}

}
