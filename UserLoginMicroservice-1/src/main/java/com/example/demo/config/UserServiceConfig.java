package com.example.demo.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.entity.UserClass;
import com.example.demo.repo.UserRepo;

@Configuration
public class UserServiceConfig implements UserDetailsService{

	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserClass byUserName = userRepo.findByName(username);
		
		if(byUserName==null) {
			throw new UsernameNotFoundException("Username Not found");
		}
		
		return new User(byUserName.getName(), byUserName.getPw(), Collections.EMPTY_LIST);
	}


}
