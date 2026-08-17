package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerGetMsgFromGitRepo {

	@Value("${msg}")
	String msg;
	
	@GetMapping("/getMsg")
	public String getMsg() {
	
		System.out.println("msg -- "+msg);
		return msg;
	}
}
