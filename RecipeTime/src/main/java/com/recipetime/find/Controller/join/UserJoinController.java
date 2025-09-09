package com.recipetime.find.Controller.join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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

    // ������� Ȯ�� �� ȸ������ ������ �̵�
    @PostMapping("/join/userRegist")
    public String userRegist(Users users, Model model) {
        // ��� üũ Ȯ��
        if (!"Y".equals(users.getAgree01()) || !"Y".equals(users.getAgree02())) {
            model.addAttribute("message", "����� ��� �����ؾ� �մϴ�.");
            return "forward:/join/siteUseAgree";
        }
        return "/join/userRegist"; // ȸ������ �� JSP
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