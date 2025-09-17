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
    CategoryService categoryService;

    // application.properties�� upload.dir �����صθ� ���Թ���
    @Value("${upload.dir:/tmp/uploads}")
    private String uploadDir;

    @GetMapping("/insert")
    public String insertForm(Model model) {
        // ī�װ� �׸�� �ɼ��� ������ JSP���� ��Ӵٿ��� ä��
//        List<CategoryItem> items = postService.listCategoryItems();
//        model.addAttribute("categoryItems", items);
        
    	// ���� = 1
        model.addAttribute("typeOptions", categoryService.getOptionsByItemId(1));
        // ��Ȳ = 2
        model.addAttribute("situationOptions", categoryService.getOptionsByItemId(2));
        // ��� = 3
        model.addAttribute("methodOptions", categoryService.getOptionsByItemId(3));
        // �ο� = 4
        model.addAttribute("peopleOptions", categoryService.getOptionsByItemId(4));
        // �丮�ð� = 5
        model.addAttribute("timeOptions", categoryService.getOptionsByItemId(5));
        // ���̵� = 6
        model.addAttribute("difficultyOptions", categoryService.getOptionsByItemId(6));
        // �ɼ��� JSP���� AJAX�� �ҷ��� �ǰ� ���⼭ �̸� �ҷ��� ������ ���� ����.
        // ���⼭�� JSP���� item���� option�� ajax�� �ҷ����� ���� ����������,
        // �������� ��� �ɼ��� �� ���� �������� API�� �߰��� ���� ����.
        return "post/insert";
    }

    @PostMapping("/insert")
    public String insertPost(
            @ModelAttribute Post post,
            @RequestParam(value="tags[]", required=false) List<String> tags,
            @RequestParam(value="ingredientName[]", required=false) List<String> ingName,
            @RequestParam(value="ingredientAmount[]", required=false) List<String> ingAmount,
            @RequestParam(value="ingredientUnit[]", required=false) List<String> ingUnit,
            @RequestParam(value="ingredientNote[]", required=false) List<String> ingNote,
            @RequestParam(value="sequenceContent[]", required=false) List<String> seqContent,
            @RequestParam(value="sequenceIngredient[]", required=false) List<String> seqIng,
            @RequestParam(value="sequenceTool[]", required=false) List<String> seqTool,
            @RequestParam(value="sequenceFire[]", required=false) List<String> seqFire,
            @RequestParam(value="sequenceTip[]", required=false) List<String> seqTip,
            @RequestParam(value="attachments[]", required=false) List<MultipartFile> files,
            @RequestParam(value="mainImage", required=false) MultipartFile mainImage,
            HttpSession session
    ) throws IOException {

        // �α����� ����� ID ���� (���ǿ��� ��������)
        String currentUserId = (String) session.getAttribute("loginUserId");
        if (currentUserId == null) {
            return "redirect:/login/login";
        }
        post.setUserid(currentUserId);

        // ��¥ �� default �ʵ� ����
        post.setRecipewritedate(LocalDate.now());
        post.setRecipedeactivate(0); // Ȱ��ȭ ����

        // �±� ��ȯ
        List<Tag> tagList = new ArrayList<>();
        if (tags != null) {
            int to = 1;
            for (String t : tags) {
                if (t != null && !t.trim().isEmpty()) {
                    Tag tag = new Tag();
                    tag.setTagname(t.trim());
                    tag.setTagorder(to++);
                    tagList.add(tag);
                }
            }
        }
        post.setTags(tagList);

        // ��� ��ȯ
        List<Ingredients> ingList = new ArrayList<>();
        if (ingName != null) {
            for (int i = 0; i < ingName.size(); i++) {
                Ingredients ig = new Ingredients();
                ig.setIngname(ingName.get(i));
                try {
                    ig.setIngquantity(Integer.valueOf(ingAmount.get(i)));
                } catch (Exception e) {
                    ig.setIngquantity(null);
                }
                ig.setUnit(ingUnit.get(i));
                ig.setExp( (ingNote != null && ingNote.size() > i) ? ingNote.get(i) : null);
                ig.setIngorder(i + 1);
                ingList.add(ig);
            }
        }
        post.setIngredients(ingList);

        // ���� ��ȯ
        List<PostSequence> seqList = new ArrayList<>();
        if (seqContent != null) {
            for (int i = 0; i < seqContent.size(); i++) {
                PostSequence ps = new PostSequence();
                ps.setExplain(seqContent.get(i));
                ps.setIngexp((seqIng != null && seqIng.size() > i) ? seqIng.get(i) : null);
                ps.setToolexp((seqTool != null && seqTool.size() > i) ? seqTool.get(i) : null);
                ps.setFireexp((seqFire != null && seqFire.size() > i) ? seqFire.get(i) : null);
                ps.setTipexp((seqTip != null && seqTip.size() > i) ? seqTip.get(i) : null);
                ps.setRecipestep(i + 1);
                seqList.add(ps);
            }
        }
        post.setSequences(seqList);

        // ���� ���� ó�� (��ǥ�̹��� + attachments)
        List<Attachment> attList = new ArrayList<>();

        // ��ǥ �̹��� (mainImage) ó��: ismain = 1
        if (mainImage != null && !mainImage.isEmpty()) {
            Attachment mainAtt = saveFileToAttachment(mainImage, true);
            attList.add(mainAtt);
        }

        // �Ϲ� ÷������
        if (files != null) {
            for (MultipartFile mf : files) {
                if (mf != null && !mf.isEmpty()) {
                    Attachment a = saveFileToAttachment(mf, false);
                    attList.add(a);
                }
            }
        }
        post.setAttachments(attList);

        // Service�� ���� (Ʈ����� ���ο��� post.recipeid �� ä����)
        postService.insertPost(post);

        return "redirect:/"; // ���� �� �������
    }

    // ���� ���� ���� ��ƿ (���� ����)
    private Attachment saveFileToAttachment(MultipartFile mf, boolean isMain) throws IOException {
        // ���� ����
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