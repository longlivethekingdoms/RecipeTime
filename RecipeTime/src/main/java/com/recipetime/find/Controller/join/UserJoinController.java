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

    // ������� ������
    @GetMapping("/join/siteUseAgree")
    public String siteUseAgree(Users users) {
        return "/join/siteUseAgree"; // JSP ���
    }

    @PostMapping("/join/userRegist")
    public String userRegist(Users users, RedirectAttributes redirectAttributes) {
        if (!"Y".equals(users.getAgree01()) || !"Y".equals(users.getAgree02())) {
            redirectAttributes.addFlashAttribute("message", "����� ��� �����ؾ� �մϴ�.");
            return "redirect:/join/siteUseAgree";
        }
        return "/join/userRegist";
    }

    // ȸ������ ó��
    @PostMapping("/join/insertUser")
    public String insertUser(Users users, Model model) {
        // ��� Ȯ��
        if (!"Y".equals(users.getAgree01()) || !"Y".equals(users.getAgree02())) {
            model.addAttribute("message", "����� ��� �����ؾ� �մϴ�.");
            return "forward:/join/siteUseAgree";
        }

        // ��ȿ�� �˻�
        String validationMessage = userService.validateUser(users);
        if (validationMessage != null) {
            model.addAttribute("message", validationMessage);
            return "forward:/join/userRegist";
        }

        // ȸ������
        userService.insertJoin(users);
        model.addAttribute("message", "ȸ�������� �Ϸ�Ǿ����ϴ�.");
        return "join/userComplete"; // �Ϸ� JSP
    }

}