package com.recipetime.find.Controller.join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recipetime.find.Model.Users;
import com.recipetime.find.Service.UserService;

@Controller
public class UserJoinController {

    @Autowired
    private UserService userService;

    // 약관동의 페이지
    @GetMapping("/join/siteUseAgree")
    public String siteUseAgree(Users users) {
        return "/join/siteUseAgree"; // JSP 경로
    }

    @PostMapping("/join/userRegist")
    public String userRegist(Users users, RedirectAttributes redirectAttributes) {
        if (!"Y".equals(users.getAgree01()) || !"Y".equals(users.getAgree02())) {
            redirectAttributes.addFlashAttribute("message", "약관에 모두 동의해야 합니다.");
            return "redirect:/join/siteUseAgree";
        }
        return "/join/userRegist";
    }

    // 회원가입 처리
    @PostMapping("/join/insertUser")
    public String insertUser(Users users, Model model) {
        // 약관 확인
        if (!"Y".equals(users.getAgree01()) || !"Y".equals(users.getAgree02())) {
            model.addAttribute("message", "약관에 모두 동의해야 합니다.");
            return "forward:/join/siteUseAgree";
        }

        // 유효성 검사
        String validationMessage = userService.validateUser(users);
        if (validationMessage != null) {
            model.addAttribute("message", validationMessage);
            return "forward:/join/userRegist";
        }

        // 회원가입
        userService.insertJoin(users);
        model.addAttribute("message", "회원가입이 완료되었습니다.");
        return "join/userComplete"; // 완료 JSP
    }

}