package com.auth.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;



public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity>{

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);
		customAuthenticationFilter.setFilterProcessesUrl("/login");
		http.addFilter(new CustomAuthenticationFilter(authenticationManager));
    }

    public static CustomDsl getCustomDsl() {
        return new CustomDsl();
    }
}
