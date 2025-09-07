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
	
	@RequestMapping(value = "/join/siteUseAgree")
	public String siteUseAgree(@ModelAttribute("users") Users users, HttpServletRequest request, ModelMap model, HttpSession session) {
		return "join/SiteUseAgree";
	}
	
	@RequestMapping(value = "/join/userRegist")
	public String userRegist(@ModelAttribute("users") Users users, HttpServletRequest request, ModelMap model, HttpSession session) {
		if(!"Y".equals(users.getAgree01())||!"Y".equals(users.getAgree02())) {
			model.addAttribute("message", "�߸� �� ����Դϴ�.");
			return "forward:/join/siteUseAgree";
		}
		return "join/UserRegist";
	}
	
//	@RequestMapping(value = "/join/duplicateCheck")
//	@ResponseBody
//	public Map<String, Object> duplicateCheck(@ModelAttribute("users") Users users) {
//	    Map<String, Object> result = new HashMap<>();
//
//	    int duplicateCnt = userService.duplicateCheck(users);
//	    if (duplicateCnt > 0) {
//	        result.put("successYn", "N");
//	        result.put("message", "�̹� ��� ���� ID �Դϴ�.");
//	    } else {
//	        result.put("successYn", "Y");
//	        result.put("message", "��� ������ ID �Դϴ�.");
//	    }
//
//	    return result;  // �ڵ����� JSON ��ȯ��
//	}
	
	@RequestMapping(value = "/join/insertUser")
	public String insertUser(@ModelAttribute("user") Users users, HttpServletRequest request, ModelMap model) {
		String validationMessage = userService.validateUser(users);
		if(validationMessage != null) {
			model.addAttribute("message", validationMessage);
			return "forward:/join/userRegist";
		}
		
		userService.insertJoin(users);
		model.addAttribute("message", "ȸ�������� �Ϸ�ƽ��ϴ�.");
		return "join/UserComplete";
	}
}
