package com.recipetime.find.Controller.post;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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

    @PostMapping("/insert")
    public String insertPost(
            Post post,
            @RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam(value="uploadFiles", required=false) MultipartFile[] uploadFiles,
            //@RequestParam Map<String, MultipartFile[]> sequenceImages,
            //@RequestParam("sequenceImages") List<List<MultipartFile>> sequenceImages,
            HttpSession session,
            HttpServletRequest request,
            RedirectAttributes ra) throws Exception {

        Users loginUser = (Users) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login/login";
        post.setUserid(loginUser.getUserid());

        if(post.getRecipewritedate() == null) post.setRecipewritedate(LocalDate.now());

        if(post.getIngredients() != null)
            for(Ingredients ing : post.getIngredients())
                if(ing.getIngquantity() == null) ing.setIngquantity(0);

        if(post.getSequences() != null)
            for(int i=0;i<post.getSequences().size();i++)
                post.getSequences().get(i).setRecipestep(i+1);

        if(post.getAttachments() == null) post.setAttachments(new ArrayList<>());

     // mainImage 처리
        if(mainImage != null && !mainImage.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String originalName = mainImage.getOriginalFilename();
            String ext = originalName.substring(originalName.lastIndexOf(".")+1);
            File dest = new File(uploadDir, uuid + "." + ext);
            mainImage.transferTo(dest);

            Attachment mainAtt = new Attachment();
            mainAtt.setFileorder(0);
            mainAtt.setIsmain(1);
            mainAtt.setFilename(originalName);
            mainAtt.setFileuuid(uuid);
            mainAtt.setFileext(ext);
            post.getAttachments().add(mainAtt);
        }
        
        // ✅ 추가 이미지 처리
        if(uploadFiles != null)
            for(MultipartFile file : uploadFiles)
                if(file != null && !file.isEmpty()){
                    String uuid = UUID.randomUUID().toString();
                    String originalName = file.getOriginalFilename();
                    String ext = originalName.substring(originalName.lastIndexOf(".")+1);
                    File dest = new File(uploadDir, uuid + "." + ext);
                    file.transferTo(dest);

                    Attachment att = new Attachment();
                    att.setIsmain(0);
                    att.setFilename(originalName);
                    att.setFileuuid(uuid);
                    att.setFileext(ext);
                    post.getAttachments().add(att);
                }      

        // 시퀀스 이미지 처리
        if (post.getSequences() != null) {
            for (int i = 0; i < post.getSequences().size(); i++) {
                PostSequence seq = post.getSequences().get(i);

                //String paramName = "sequences[" + i + "]";
                //MultipartFile[] seqFiles = ((MultipartHttpServletRequest) request).getFiles(paramName).toArray(new MultipartFile[0]);
                List<MultipartFile> seqFiles = seq.getImages();
                if (seqFiles != null && seqFiles.size() > 0) {
                    List<Attachment> seqAtts = new ArrayList<>();
                    for (MultipartFile file : seqFiles) {
                        if (!file.isEmpty()) {
                        	String uuid = UUID.randomUUID().toString();
                            String originalName = file.getOriginalFilename();
                            String ext = originalName.substring(originalName.lastIndexOf(".")+1);
                            File dest = new File(uploadDir, uuid + "." + ext);
                            file.transferTo(dest);
                            
                            Attachment att = new Attachment();
                            att.setFilename(file.getOriginalFilename());
                            att.setFileuuid(uuid);
                            att.setFileext(ext);
                            seqAtts.add(att);
                        }
                        
                    }
                    System.out.println("시퀀스 이미지");
                    
                    seq.setAttachments(seqAtts);
                }
            }
        }
        
        postService.insertPost(post);
        
        return "redirect:/post/list";
    }
    
    //20251014 아래부터
    
    // ✅ 레시피 수정 폼(GET)
    @GetMapping("/edit/{recipeid}")
    public String editForm(@PathVariable("recipeid") int recipeid, Model model, HttpSession session) {
        Users loginUser = (Users) session.getAttribute("loginUser");

        // 로그인 안 된 경우 → 로그인 페이지로
        if (loginUser == null) {
            return "redirect:/login/login";
        }

        String loginUserId = loginUser.getUserid();
        String accessLevel = loginUser.getAccesslevel();

        Post post = postService.getPostById(recipeid, loginUserId, accessLevel);
        if (post == null) {
            return "redirect:/post/detail/" + recipeid;
        }

        // 🔒 권한 체크 (작성자 또는 관리자만 허용)
        if (!loginUserId.equals(post.getUserid()) && !"manager".equalsIgnoreCase(accessLevel)) {
            return "redirect:/post/detail/" + recipeid + "?error=forbidden";
        }

        // 카테고리 옵션들 추가
        model.addAttribute("post", post);
        model.addAttribute("typeOptions", categoryService.getOptionsByItemId(1));
        model.addAttribute("situationOptions", categoryService.getOptionsByItemId(2));
        model.addAttribute("methodOptions", categoryService.getOptionsByItemId(3));
        model.addAttribute("peopleOptions", categoryService.getOptionsByItemId(4));
        model.addAttribute("timeOptions", categoryService.getOptionsByItemId(5));
        model.addAttribute("difficultyOptions", categoryService.getOptionsByItemId(6));

        return "post/edit";
    }
    
 // 수정 처리(POST)
    @PostMapping("/edit/{recipeid}")
    public String editPost(@PathVariable("recipeid") int recipeid,
                           Post post,
                           @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
                           @RequestParam(value = "uploadFiles", required = false) MultipartFile[] uploadFiles,
                           HttpSession session) throws Exception {

        Users loginUser = (Users) session.getAttribute("loginUser");
        String loginUserId = null;
        String accessLevel = null;
        if (loginUser != null) {
            loginUserId = loginUser.getUserid();
            accessLevel = loginUser.getAccesslevel();
        }

        // 권한 체크: 작성자 또는 manager만 수정 가능
        Post original = postService.getPostById(recipeid, loginUserId, accessLevel);
        if (original == null) {
            return "redirect:/post/detail/" + recipeid;
        }

        // 여기에 mainImage / uploadFiles 처리 로직(너의 insertPost와 동일한 방식으로 적용)
        // -> 너가 원하면 기존 insertPost의 이미지 처리 코드를 그대로 복붙해서 파일 저장, Attachment 세팅 후 post에 넣어줘

        post.setRecipeid(recipeid);
        post.setUserid(original.getUserid()); // 작성자 유지
        postService.updatePost(post); // service에 updatePost가 있어야 함 (아래에 샘플 포함) 만들어야함.
        return "redirect:/post/detail/" + recipeid;
    }
    
    // 비활성화(삭제) 처리
    @PostMapping("/deactivate/{recipeid}")
    public String deactivatePost(@PathVariable("recipeid") int recipeid, HttpSession session) {
        Users loginUser = (Users) session.getAttribute("loginUser");
        String loginUserId = null;
        String accessLevel = null;
        if (loginUser != null) {
            loginUserId = loginUser.getUserid();
            accessLevel = loginUser.getAccesslevel();
        }

        Post post = postService.getPostById(recipeid, loginUserId, accessLevel);
        if (post == null) {
            return "redirect:/post/detail/" + recipeid;
        }

        // 이제 실제 비활성화: service 호출
        postService.deactivatePost(recipeid);
        return "redirect:/post/list";
    }
    
    //2025-10-14 위까지
    
    @GetMapping("/dummy")
    String dummy(Post post,
    		HttpSession session) {
    	Users loginUser = (Users) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login/login";
        post.setUserid(loginUser.getUserid());
    	postService.dummy(loginUser);
    	
    	return "redirect:/post/list";
    }
    
    @GetMapping("/init")
    String init() {
    	postService.init();
    	
    	
    	return "redirect:/post/list";
    }
}