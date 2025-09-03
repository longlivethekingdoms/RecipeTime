package com.recipetime.find.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.recipetime.find.Service.UserService;

@Controller
public class LobbyController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	String index() {
		return "index";
	}
}
