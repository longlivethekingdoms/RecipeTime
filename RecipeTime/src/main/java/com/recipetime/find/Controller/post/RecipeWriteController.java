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

    // application.properties에 upload.dir 설정해두면 주입받음
    @Value("${upload.dir:/tmp/uploads}")
    private String uploadDir;

    @GetMapping("/insert")
    public String insertForm(Model model) {
        // 카테고리 항목과 옵션을 보내서 JSP에서 드롭다운을 채움
//        List<CategoryItem> items = postService.listCategoryItems();
//        model.addAttribute("categoryItems", items);
        
    	// 종류 = 1
        model.addAttribute("typeOptions", categoryService.getOptionsByItemId(1));
        // 상황 = 2
        model.addAttribute("situationOptions", categoryService.getOptionsByItemId(2));
        // 방법 = 3
        model.addAttribute("methodOptions", categoryService.getOptionsByItemId(3));
        // 인원 = 4
        model.addAttribute("peopleOptions", categoryService.getOptionsByItemId(4));
        // 요리시간 = 5
        model.addAttribute("timeOptions", categoryService.getOptionsByItemId(5));
        // 난이도 = 6
        model.addAttribute("difficultyOptions", categoryService.getOptionsByItemId(6));
        // 옵션은 JSP에서 AJAX로 불러도 되고 여기서 미리 불러서 전송할 수도 있음.
        // 여기서는 JSP에서 item별로 option을 ajax로 불러오는 것을 권장하지만,
        // 간단히는 모든 옵션을 한 번에 가져오는 API를 추가할 수도 있음.
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

        // 로그인한 사용자 ID 설정 (세션에서 가져오기)
        String currentUserId = (String) session.getAttribute("loginUserId");
        if (currentUserId == null) {
            return "redirect:/login/login";
        }
        post.setUserid(currentUserId);

        // 날짜 및 default 필드 설정
        post.setRecipewritedate(LocalDate.now());
        post.setRecipedeactivate(0); // 활성화 상태

        // 태그 변환
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

        // 재료 변환
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

        // 순서 변환
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

        // 파일 저장 처리 (대표이미지 + attachments)
        List<Attachment> attList = new ArrayList<>();

        // 대표 이미지 (mainImage) 처리: ismain = 1
        if (mainImage != null && !mainImage.isEmpty()) {
            Attachment mainAtt = saveFileToAttachment(mainImage, true);
            attList.add(mainAtt);
        }

        // 일반 첨부파일
        if (files != null) {
            for (MultipartFile mf : files) {
                if (mf != null && !mf.isEmpty()) {
                    Attachment a = saveFileToAttachment(mf, false);
                    attList.add(a);
                }
            }
        }
        post.setAttachments(attList);

        // Service로 저장 (트랜잭션 내부에서 post.recipeid 가 채워짐)
        postService.insertPost(post);

        return "redirect:/"; // 저장 후 목록으로
    }

    // 파일 실제 저장 유틸 (간단 구현)
    private Attachment saveFileToAttachment(MultipartFile mf, boolean isMain) throws IOException {
        // 폴더 생성
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