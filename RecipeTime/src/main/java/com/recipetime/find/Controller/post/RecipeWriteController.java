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

    // �젅�떆�뵾 �옉�꽦 �뤌(GET)
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

    // �젅�떆�뵾 �벑濡� 泥섎━(POST)
    @PostMapping("/insert")
    public String insertPost(
            @ModelAttribute Post post,
            @RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam(value="uploadFiles", required=false) MultipartFile[] uploadFiles,
            HttpSession session,
            RedirectAttributes ra) {
    		System.out.println(mainImage);
    		System.out.println(uploadFiles);
        // 濡쒓렇�씤 泥댄겕
        Users loginUser = (Users) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login/login";
        post.setUserid(loginUser.getUserid());

        // �옉�꽦�씪 湲곕낯媛�
        if(post.getRecipewritedate() == null) post.setRecipewritedate(LocalDate.now());

        // �옱猷� �닔�웾 湲곕낯媛�
        if(post.getIngredients() != null) {
            for(Ingredients ing : post.getIngredients()) {
                if(ing.getIngquantity() == null) ing.setIngquantity(0);
            }
        }

        // �깭洹� �닚�꽌
        if(post.getTags() != null) {
            for(int i=0; i<post.getTags().size(); i++)
                post.getTags().get(i).setTagorder(i+1);
        }

        // �떆���뒪 �닚�꽌
        if(post.getSequences() != null) {
            for(int i=0; i<post.getSequences().size(); i++)
                post.getSequences().get(i).setRecipestep(i+1);
        }

        // attachments 珥덇린�솕
        if(post.getAttachments() == null) post.setAttachments(new ArrayList<>());

        // ���몴 �씠誘몄� 泥섎━
        if(mainImage != null && !mainImage.isEmpty()) {
            Attachment mainAtt = new Attachment();
            mainAtt.setIsmain(1);
            mainAtt.setFilename(mainImage.getOriginalFilename());
            mainAtt.setFileuuid(UUID.randomUUID().toString());
            // TODO: �꽌踰� ���옣
            post.getAttachments().add(mainAtt);
        }

        // 異붽� �씠誘몄� 泥섎━
        if(uploadFiles != null) {
            for(MultipartFile file : uploadFiles) {
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