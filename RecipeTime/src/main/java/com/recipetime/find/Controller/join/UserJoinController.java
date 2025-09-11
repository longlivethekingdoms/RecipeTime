package com.recipetime.find.Controller.join;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recipetime.find.Model.Users;
import com.recipetime.find.Service.UserService;

@Controller
@RequestMapping("/join")
public class UserJoinController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/addUserAdmin")
    @ResponseBody
    public ResponseEntity<String> addUserAdmin(@RequestBody Users user) {
        userService.insertUserAdmin(user);
        return ResponseEntity.ok("������ �߰� ����");
    }

    // ������� ������
    @GetMapping("/siteUseAgree")
    public String siteUseAgree(Users users) {
        return "/join/siteUseAgree"; // JSP ���
    }
    
    @GetMapping("/userRegist")
    public String userRegistPage(Users users, RedirectAttributes redirectAttributes) {
        if (!"Y".equals(users.getAgree01()) || !"Y".equals(users.getAgree02())) {
            redirectAttributes.addAttribute("message", "�߸��� �����Դϴ�. ����� �������ּ���.");
            return "redirect:/join/siteUseAgree";
        }
        return "/join/userRegist";
    }

    @PostMapping("/userRegist")
    public String userRegistSubmit(HttpServletRequest request, Users users, RedirectAttributes redirectAttributes) {
        String agree01 = request.getParameter("agree01");
        String agree02 = request.getParameter("agree02");

        if (!"Y".equals(agree01) || !"Y".equals(agree02)) {
            redirectAttributes.addAttribute("message", "����� ��� �����ؾ� �մϴ�.");
            return "redirect:/join/siteUseAgree";
        }
        return "/join/userRegist";
    }

    // ȸ������ ó��
    @PostMapping("/insertUser")
    public String insertUser(Users users, Model model) {
        // ��� Ȯ��
        if (!"Y".equals(users.getAgree01()) || !"Y".equals(users.getAgree02())) {
            model.addAttribute("message", "����� ��� �����ؾ� �մϴ�.");
            return "forward:/join/siteUseAgree";
        }

//        // ��ȿ�� �˻�
//        String validationMessage = userService.validateUser(users);
//        if (validationMessage != null) {
//            model.addAttribute("message", validationMessage);
//            return "forward:/join/userRegist";
//        }
        
        // ȸ������
        userService.insertJoin(users);
        model.addAttribute("message", "ȸ�������� �Ϸ�Ǿ����ϴ�.");
        return "join/userComplete"; // �Ϸ� JSP
    }
    
    @GetMapping("/userComplete")
    public String userComplete(Users users, Model model, RedirectAttributes redirectAttributes) {
        // ��� ���� ���� Ȯ��
        if (!"Y".equals(users.getAgree01()) || !"Y".equals(users.getAgree02())) {
            redirectAttributes.addAttribute("message", "�߸��� �����Դϴ�. ����� �������ּ���.");
            return "redirect:/join/siteUseAgree";
        }
        
//        // ��ȿ�� �˻�
//        String validationMessage = userService.validateUser(users);
//        if (validationMessage != null) {
//            model.addAttribute("message", validationMessage);
//            return "forward:/join/userRegist";
//        }
        
        return "/join/userComplete";
    }
    
    @GetMapping("/duplicateCheckID")
    @ResponseBody
    public Map<String, Object> duplicateCheckID(String userid, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        boolean isDuplicate = userService.duplicateID(userid);
        result.put("isDuplicate", isDuplicate);
        result.put("message", isDuplicate ? "�̹� ��� ���� ID�Դϴ�." : "��� ������ ID�Դϴ�.");
        
        // �ߺ� Ȯ�� �Ϸ�� ���� �Ǵ� �÷��� ����
        session.setAttribute("isduplicateIDCheck", !isDuplicate);
        return result;
    }

    @GetMapping("/duplicateCheckNickname")
    @ResponseBody
    public Map<String, Object> duplicateCheckNickname(String nickname, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        boolean isDuplicate = userService.duplicateNickname(nickname);
        result.put("isDuplicate", isDuplicate);
        result.put("message", isDuplicate ? "�̹� ��� ���� �г����Դϴ�." : "��� ������ �г����Դϴ�.");
        session.setAttribute("isduplicateNickname", !isDuplicate);
        return result;
    }

    @GetMapping("/duplicateCheckEmail")
    @ResponseBody
    public Map<String, Object> duplicateCheckEmail(String useremail, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        boolean isDuplicate = userService.duplicateEmail(useremail);
        result.put("isDuplicate", isDuplicate);
        result.put("message", isDuplicate ? "�̹� ��� ���� �̸����Դϴ�." : "��� ������ �̸����Դϴ�.");
        session.setAttribute("isduplicateEmail", !isDuplicate);
        return result;
    }

}