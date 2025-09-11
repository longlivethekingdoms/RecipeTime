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
        return ResponseEntity.ok("관리자 추가 성공");
    }

    // 약관동의 페이지
    @GetMapping("/siteUseAgree")
    public String siteUseAgree(Users users) {
        return "/join/siteUseAgree"; // JSP 경로
    }
    
    @GetMapping("/userRegist")
    public String userRegistPage(Users users, RedirectAttributes redirectAttributes) {
        if (!"Y".equals(users.getAgree01()) || !"Y".equals(users.getAgree02())) {
            redirectAttributes.addAttribute("message", "잘못된 접근입니다. 약관에 동의해주세요.");
            return "redirect:/join/siteUseAgree";
        }
        return "/join/userRegist";
    }

    @PostMapping("/userRegist")
    public String userRegistSubmit(HttpServletRequest request, Users users, RedirectAttributes redirectAttributes) {
        String agree01 = request.getParameter("agree01");
        String agree02 = request.getParameter("agree02");

        if (!"Y".equals(agree01) || !"Y".equals(agree02)) {
            redirectAttributes.addAttribute("message", "약관에 모두 동의해야 합니다.");
            return "redirect:/join/siteUseAgree";
        }
        return "/join/userRegist";
    }

    // 회원가입 처리
    @PostMapping("/insertUser")
    public String insertUser(Users users, Model model) {
        // 약관 확인
        if (!"Y".equals(users.getAgree01()) || !"Y".equals(users.getAgree02())) {
            model.addAttribute("message", "약관에 모두 동의해야 합니다.");
            return "forward:/join/siteUseAgree";
        }

//        // 유효성 검사
//        String validationMessage = userService.validateUser(users);
//        if (validationMessage != null) {
//            model.addAttribute("message", validationMessage);
//            return "forward:/join/userRegist";
//        }
        
        // 회원가입
        userService.insertJoin(users);
        model.addAttribute("message", "회원가입이 완료되었습니다.");
        return "join/userComplete"; // 완료 JSP
    }
    
    @GetMapping("/userComplete")
    public String userComplete(Users users, Model model, RedirectAttributes redirectAttributes) {
        // 약관 동의 여부 확인
        if (!"Y".equals(users.getAgree01()) || !"Y".equals(users.getAgree02())) {
            redirectAttributes.addAttribute("message", "잘못된 접근입니다. 약관에 동의해주세요.");
            return "redirect:/join/siteUseAgree";
        }
        
//        // 유효성 검사
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
        result.put("message", isDuplicate ? "이미 사용 중인 ID입니다." : "사용 가능한 ID입니다.");
        
        // 중복 확인 완료시 세션 또는 플래그 설정
        session.setAttribute("isduplicateIDCheck", !isDuplicate);
        return result;
    }

    @GetMapping("/duplicateCheckNickname")
    @ResponseBody
    public Map<String, Object> duplicateCheckNickname(String nickname, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        boolean isDuplicate = userService.duplicateNickname(nickname);
        result.put("isDuplicate", isDuplicate);
        result.put("message", isDuplicate ? "이미 사용 중인 닉네임입니다." : "사용 가능한 닉네임입니다.");
        session.setAttribute("isduplicateNickname", !isDuplicate);
        return result;
    }

    @GetMapping("/duplicateCheckEmail")
    @ResponseBody
    public Map<String, Object> duplicateCheckEmail(String useremail, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        boolean isDuplicate = userService.duplicateEmail(useremail);
        result.put("isDuplicate", isDuplicate);
        result.put("message", isDuplicate ? "이미 사용 중인 이메일입니다." : "사용 가능한 이메일입니다.");
        session.setAttribute("isduplicateEmail", !isDuplicate);
        return result;
    }

}