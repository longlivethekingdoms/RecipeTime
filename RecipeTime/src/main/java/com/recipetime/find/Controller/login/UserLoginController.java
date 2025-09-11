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
            session.setAttribute("loginUser", loginUser); // ���ǿ� ����
            return "redirect:/"; // �α��� ���� �� ��������
        } else {
            model.addAttribute("message", "���̵� �Ǵ� ��й�ȣ�� �ùٸ��� �ʽ��ϴ�.");
            return "/login/login"; // �ٽ� �α��� ������
        }
    }
	
	@GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login/login"; // �α��� ��������
    }
}
