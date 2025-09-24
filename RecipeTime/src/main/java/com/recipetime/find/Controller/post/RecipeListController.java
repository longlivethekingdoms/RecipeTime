package com.recipetime.find.Controller.post;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recipetime.find.Model.Post;
import com.recipetime.find.Service.PostService;

@Controller
@RequestMapping("/post")
public class RecipeListController {
	
	@Autowired
	PostService service;
	
	 @GetMapping("/list")
	    public String recipeList(
	            @RequestParam(value = "categoryOptions", required = false) List<Integer> categoryOptions,
	            @RequestParam(value = "page", defaultValue = "1") int page,
	            @RequestParam(value = "size", defaultValue = "9") int size,
	            Model model) {

	        // ✅ 전체 게시글 개수
	        int totalCount = service.getRecipeCount(categoryOptions);

	        // ✅ 페이지 계산
	        int totalPages = (int) Math.ceil((double) totalCount / size);
	        if (page < 1) page = 1;
	        if (page > totalPages) page = totalPages;

	        int offset = (page - 1) * size;

	        // ✅ 레시피 목록 가져오기 (필터 + 페이징 적용)
	        List<Post> recipeList = service.getRecipeList(categoryOptions, offset, size);

	        // ✅ 카테고리 아이템 + 옵션 가져오기
	        List<Map<String, Object>> categoryItems = service.getCategoryItems();
	        for (Map<String, Object> item : categoryItems) {
	            int itemid = (int) item.get("itemid");
	            List<Map<String, Object>> options = service.getCategoryOptionsByItem(itemid);
	            item.put("options", options);
	        }

	        // ✅ 모델에 데이터 전달
	        model.addAttribute("recipeList", recipeList);
	        model.addAttribute("categoryItems", categoryItems);
	        model.addAttribute("selectedOptions", categoryOptions);

	        // ✅ 페이지네이션 데이터 전달
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", totalPages);

	        return "post/list"; // → /WEB-INF/views/recipe/list.jsp
	    }
}
