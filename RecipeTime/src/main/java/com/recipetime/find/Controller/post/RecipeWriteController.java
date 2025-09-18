package com.recipetime.find.Controller.post;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * �۾��� ��
     */
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

    /**
     * �� ����
     */
    @PostMapping("/insert")
    public String insertPost(
            @ModelAttribute Post post,                              // �ڵ� ���ε�
            @RequestParam(value="mainImage", required=false) MultipartFile mainImage,
            @RequestParam(value="attachments", required=false) List<MultipartFile> files,
            HttpSession session
    ) throws IOException {

        // �α��� üũ
        String currentUserId = (String) session.getAttribute("loginUserId");
        if (currentUserId == null) {
            return "redirect:/login/login";
        }
        post.setUserid(currentUserId);

        // ��¥ �� �⺻��
        post.setRecipewritedate(LocalDate.now());
        post.setRecipedeactivate(0);

        // ��� ���� �⺻�� ó��
        if (post.getIngredients() != null) {
            for (Ingredients ing : post.getIngredients()) {
                if (ing.getIngquantity() == null) {
                    ing.setIngquantity(0);
                }
            }
        }

        // �±� ó��: ���� �ο�
        if (post.getTags() != null) {
            int order = 1;
            for (Tag t : post.getTags()) {
                t.setTagorder(order++);
            }
        }

        // �丮 ����: step ��ȣ �ο�
        if (post.getSequences() != null) {
            int step = 1;
            for (PostSequence ps : post.getSequences()) {
                ps.setRecipestep(step++);
            }
        }

        // ���� ó��
        List<Attachment> attList = new ArrayList<>();
        if (mainImage != null && !mainImage.isEmpty()) {
            attList.add(saveFileToAttachment(mainImage, true));
        }
        if (files != null) {
            for (MultipartFile mf : files) {
                if (mf != null && !mf.isEmpty()) {
                    attList.add(saveFileToAttachment(mf, false));
                }
            }
        }
        post.setAttachments(attList);

        // DB ����
        postService.insertPost(post);

        return "redirect:/"; //post/list�� ��ȯ�� ��
    }

    /**
     * ���� ���� ��ƿ
     */
    private Attachment saveFileToAttachment(MultipartFile mf, boolean isMain) throws IOException {
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String original = mf.getOriginalFilename();
        String ext = "";
        if (original != null && original.contains(".")) {
            ext = original.substring(original.lastIndexOf('.') + 1);
        }

        String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
        String saveName = uuid + (ext.isEmpty() ? "" : ("." + ext));

        File out = new File(dir, saveName);
        mf.transferTo(out);

        Attachment att = new Attachment();
        att.setFilename(original);
        att.setFileuuid(saveName);
        att.setFileext(ext);
        att.setIsmain(isMain ? 1 : 0);

        return att;
    }
}