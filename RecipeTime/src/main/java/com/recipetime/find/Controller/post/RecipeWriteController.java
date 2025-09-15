package com.recipetime.find.Controller.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.recipetime.find.Model.Post;
import com.recipetime.find.Service.PostService;

@Controller
@RequestMapping("/post")
public class RecipeWriteController {

	@Autowired
    private PostService postService;

    @GetMapping("/insert")
    public String insertForm(Model model) {
        model.addAttribute("post", new Post());
        return "post/insert"; // insert.jsp
    }

    @PostMapping("/insert")
    public String insertPost(@ModelAttribute Post post) {
        postService.insertPost(post);
        return "redirect:/post/list";
    }
    
    @PostMapping("/delete/{recipeid}")
    public String deletePost(@PathVariable int recipeid) {
        postService.deactivatePost(recipeid); // 서비스 호출
        return "redirect:/post/list"; // 목록으로 리다이렉트
    }
}
