package com.recipetime.find.Controller.post;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recipetime.find.Model.CategoryItem;
import com.recipetime.find.Model.Post;
import com.recipetime.find.Model.RecipeSearchPager;
import com.recipetime.find.Model.Users;
import com.recipetime.find.Service.PostService;
import com.recipetime.find.pager.Pager;

@Controller
@RequestMapping("/post")
public class RecipeListController {
	
	@Autowired
	PostService service;
	
	@GetMapping("/list")
	String list(Model model, RecipeSearchPager recipesearchpager, HttpSession session) {
		
		List<CategoryItem> categoryItem = service.listCategoryItems();
		model.addAttribute("categoryList", categoryItem);
		
		// ✅ 로그인 유저 확인
        Users loginUser = (Users) session.getAttribute("loginUser");
        String userId = null;
        String accessLevel = "normal"; // 기본값: 비로그인 시 일반 사용자처럼 처리

        if (loginUser != null) {
            userId = loginUser.getUserid();
            accessLevel = loginUser.getAccesslevel(); // "manager" or "normal"
        }

        // ✅ 로그인 정보 전달
        recipesearchpager.setLoginUserId(userId);
        recipesearchpager.setAccessLevel(accessLevel);

        // ✅ 필터링된 레시피 목록 가져오기
        List<Post> postlist = service.postlist(recipesearchpager);
        model.addAttribute("recipeList", postlist);	
		
		return "/post/list";

	}
}
