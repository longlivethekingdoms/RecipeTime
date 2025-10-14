package com.recipetime.find.Controller.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recipetime.find.Model.Post;
import com.recipetime.find.Model.Users;
import com.recipetime.find.Service.PostService;

@Controller
@RequestMapping("/post")
public class RecipeDetailController {

    @Autowired
    private PostService postService;

    @GetMapping("/detail/{recipeid}")
    public String detail(@PathVariable("recipeid") int recipeid, Model model, HttpSession session) {
    	// ���ǿ��� �α����� ����� ���� ��������
        Users loginUser = (Users) session.getAttribute("loginUser");
        
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("recipeid", recipeid);

        // �α��� �� �� ��� (�Խ�Ʈ)
        if (loginUser == null) {
            paramMap.put("loginUserId", null);
            paramMap.put("accessLevel", "guest");
        } else {
            paramMap.put("loginUserId", loginUser.getUserid());
            paramMap.put("accessLevel", loginUser.getAccesslevel());
        }

        Post post = postService.getPostDetail(paramMap);

        if (post == null) {
            return "error/404"; // ���� ���� ���ų� ������ �Խñ�
        }

        model.addAttribute("post", post);
        return "post/detail";
    }
}