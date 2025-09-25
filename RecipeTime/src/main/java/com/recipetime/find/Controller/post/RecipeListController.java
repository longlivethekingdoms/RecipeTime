package com.recipetime.find.Controller.post;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recipetime.find.Model.CategoryItem;
import com.recipetime.find.Model.Post;
import com.recipetime.find.Model.RecipeSearchPager;
import com.recipetime.find.Service.PostService;
import com.recipetime.find.pager.Pager;

@Controller
@RequestMapping("/post")
public class RecipeListController {
	
	@Autowired
	PostService service;
	
	@GetMapping("/list")
	String list(Model model, RecipeSearchPager recipesearchpager) {
		List<CategoryItem> categoryItem = service.listCategoryItems();
		
		model.addAttribute("categoryList", categoryItem);
		
		List<Post> postlist = service.postlist(recipesearchpager);
		
		model.addAttribute("recipeList", postlist);
		
		System.out.println(postlist.get(1).getRecipewritedate());
		
		return "/post/list";

	}
}
