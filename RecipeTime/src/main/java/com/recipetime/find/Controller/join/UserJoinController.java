package com.recipetime.find.Controller.join;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.recipetime.find.Model.Users;
import com.recipetime.find.Service.UserService;

@Controller
@RequestMapping("/join")
public class UserJoinController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/addUserAdmin")
	public String addUserAdmin(@ModelAttribute("user") Users users, HttpServletRequest request, ModelMap model, HttpSession session) {
		userService.insertUserAdmin(users);
		return null;
	}
	
	@RequestMapping(value = "/join/siteUseAgree")
	public String siteUseAgree(@ModelAttribute("users") Users users, HttpServletRequest request, ModelMap model, HttpSession session) {
		return "join/SiteUseAgree";
	}
	
	@RequestMapping(value = "/join/userRegist")
	public String userRegist(@ModelAttribute("users") Users users, HttpServletRequest request, ModelMap model, HttpSession session) {
		if(!"Y".equals(users.getAgree01())||!"Y".equals(users.getAgree02())) {
			model.addAttribute("message", "잘못 된 경로입니다.");
			return "forward:/join/siteUseAgree";
		}
		return "join/UserRegist";
	}
	
	@RequestMapping(value = "/join/insertUser")
	public String insertUser(@ModelAttribute("user") Users users, HttpServletRequest request, ModelMap model) {
		String validationMessage = userService.validateUser(users);
		if(validationMessage != null) {
			model.addAttribute("message", validationMessage);
			return "forward:/join/userRegist";
		}
		
		userService.insertJoin(users);
		model.addAttribute("message", "회원가입이 완료됐습니다.");
		return "join/UserComplete";
	}
}
