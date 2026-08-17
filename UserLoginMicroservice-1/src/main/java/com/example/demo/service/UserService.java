package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.JWTService;
import com.example.demo.entity.UserClass;
import com.example.demo.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private PasswordEncoder pwEncoder;
	
	@Autowired
	private JWTService jwtService;
	
	public String userLogin(UserClass user) {
		Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPw()));
		if(authenticate.isAuthenticated()) {
			return jwtService.generateToken(user.getName());
		}
		
		return "Invalid UserName or PW";
	}
	
	public UserClass register(UserClass user) {
		UserClass userClass = new UserClass();
		userClass.setName(user.getName());
		userClass.setPw(pwEncoder.encode(user.getPw()));
		return userRepo.save(userClass);
		
	}
}
