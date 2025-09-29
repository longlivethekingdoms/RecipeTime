package com.recipetime.find.Controller.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recipetime.find.Model.Post;
import com.recipetime.find.Service.PostService;

@Controller
@RequestMapping("/post")
public class RecipeDetailController {

    @Autowired
    private PostService postService;

    @GetMapping("/detail/{recipeid}")
    public String detail(@PathVariable int recipeid, Model model) {
        Post post = postService.getPostDetail(recipeid);
        if (post == null) return "error/404";

        model.addAttribute("post", post);
        
        return "post/detail";
    }
}