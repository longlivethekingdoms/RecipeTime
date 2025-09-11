package com.recipetime.find.Controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.recipetime.find.Service.UserService;

@Controller
@RequestMapping("/login")
public class UserLoginController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/login")
	String login() {
		return "login";
	}
}
