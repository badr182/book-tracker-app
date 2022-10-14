package io.javabrains.utils;

import javax.servlet.http.HttpServletRequest;

import io.javabrains.model.User;

public interface AuthenticationService {
	
	User getUser(HttpServletRequest request);
}
