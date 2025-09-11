package com.recipetime.find.Controller.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.recipetime.find.Model.Users;
import com.recipetime.find.Service.UserService;

@Controller
@RequestMapping("/login")
public class UserLoginController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/login")
	String login() {
		return "login/login";
	}
	
	@PostMapping("/doLogin")
	public String doLogin(Users users, HttpSession session, Model model) {
        Users loginUser = userService.login(users.getUserid(), users.getUserpw());

        if (loginUser != null) {
            session.setAttribute("loginUser", loginUser); // 세션에 저장
            return "redirect:/"; // 로그인 성공 시 메인으로
        } else {
            model.addAttribute("message", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "/login/login"; // 다시 로그인 페이지
        }
    }
	
	@GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login/login"; // 로그인 페이지로
    }
}
