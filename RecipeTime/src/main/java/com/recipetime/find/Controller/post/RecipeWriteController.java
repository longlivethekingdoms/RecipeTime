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