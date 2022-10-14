package com.auth.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails ;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.domain.User;
import com.auth.repositories.UserRepository;
import com.datastax.oss.driver.api.core.uuid.Uuids;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@Override
	public User save(User user) {
		log.info("Saving new user {}  to db ", user.getUsername());	
		user.setId(Uuids.timeBased().toString());		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public User getUser(String username) {
		return userRepo.findByUsername(username).orElse(null);
		
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {			
		Optional<User> user = userRepo.findByUsername(username);
		// user.
		if (!user.isPresent()) {
			log.error("User not found in the db");
			throw new UsernameNotFoundException("User not found in the db");
		}else {
			log.info("User found in the database: {}",username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
//		user.getRoles().forEach( role -> { 
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		});
		return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), authorities);
	}
}
