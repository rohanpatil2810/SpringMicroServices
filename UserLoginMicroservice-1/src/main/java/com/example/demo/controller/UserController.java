package com.example.demo.controller;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserClass;
import com.example.demo.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> userRegister(@RequestBody UserClass user){
		userService.register(user);
		return ResponseEntity.status(HttpStatus.SC_CREATED)
        .body("User saved");

	}
	
	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody UserClass user){
	
		String userLoginToken = userService.userLogin(user);
		
		if(userLoginToken!=null) {
			return ResponseEntity.ok(userLoginToken);
		}
		return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED)
                .body("Invalid Username or Password");
	}
}
