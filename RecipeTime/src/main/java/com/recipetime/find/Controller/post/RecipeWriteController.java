package com.recipetime.find.Controller.post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recipetime.find.Model.*;
import com.recipetime.find.Service.CategoryService;
import com.recipetime.find.Service.PostService;

@Controller
@RequestMapping("/post")
public class RecipeWriteController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private CategoryService categoryService;

    @Value("${upload.dir:/tmp/uploads}")
    private String uploadDir;

    // 레시피 작성 폼(GET)
    @GetMapping("/insert")
    public String insertForm(Model model) {
        model.addAttribute("typeOptions", categoryService.getOptionsByItemId(1));
        model.addAttribute("situationOptions", categoryService.getOptionsByItemId(2));
        model.addAttribute("methodOptions", categoryService.getOptionsByItemId(3));
        model.addAttribute("peopleOptions", categoryService.getOptionsByItemId(4));
        model.addAttribute("timeOptions", categoryService.getOptionsByItemId(5));
        model.addAttribute("difficultyOptions", categoryService.getOptionsByItemId(6));
        return "post/insert";
    }

    // 레시피 등록 처리(POST)
    @PostMapping("/insert")
    public String insertPost(
            @ModelAttribute Post post,
            @RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam(value="attachments", required=false) MultipartFile[] attachments,
            HttpSession session,
            RedirectAttributes ra) {

        // 로그인 체크
        Users loginUser = (Users) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login/login";
        post.setUserid(loginUser.getUserid());

        // 작성일 기본값
        if(post.getRecipewritedate() == null) post.setRecipewritedate(LocalDate.now());

        // 재료 수량 기본값
        if(post.getIngredients() != null) {
            for(Ingredients ing : post.getIngredients()) {
                if(ing.getIngquantity() == null) ing.setIngquantity(0);
            }
        }

        // 태그 순서
        if(post.getTags() != null) {
            for(int i=0; i<post.getTags().size(); i++)
                post.getTags().get(i).setTagorder(i+1);
        }

        // 시퀀스 순서
        if(post.getSequences() != null) {
            for(int i=0; i<post.getSequences().size(); i++)
                post.getSequences().get(i).setRecipestep(i+1);
        }

        // attachments 초기화
        if(post.getAttachments() == null) post.setAttachments(new ArrayList<>());

        // 대표 이미지 처리
        if(mainImage != null && !mainImage.isEmpty()) {
            Attachment mainAtt = new Attachment();
            mainAtt.setIsmain(1);
            mainAtt.setFilename(mainImage.getOriginalFilename());
            mainAtt.setFileuuid(UUID.randomUUID().toString());
            // TODO: 서버 저장
            post.getAttachments().add(mainAtt);
        }

        // 추가 이미지 처리
        if(attachments != null) {
            for(MultipartFile file : attachments) {
                if(file != null && !file.isEmpty()) {
                    Attachment att = new Attachment();
                    att.setIsmain(0);
                    att.setFilename(file.getOriginalFilename());
                    att.setFileuuid(UUID.randomUUID().toString());
                    post.getAttachments().add(att);
                }
            }
        }

        postService.insertPost(post);
        return "redirect:/";
    }
}