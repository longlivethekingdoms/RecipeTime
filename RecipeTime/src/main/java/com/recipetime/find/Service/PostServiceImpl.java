package com.recipetime.find.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.recipetime.find.DAO.PostDAO;
import com.recipetime.find.Model.*;
import com.recipetime.find.pager.Pager;

@Service
public class PostServiceImpl implements PostService {

	 @Autowired
	    private PostDAO postDAO;

	    @Override
	    @Transactional
	    public void insertPost(Post post) {
	        // 1) recipepost insert -> post.recipeid 채워짐 (useGeneratedKeys in mapper)
	        postDAO.insertPost(post);
	        int recipeId = post.getRecipeid();

	        // 2) tags (batch)
	        if (post.getTags() != null && !post.getTags().isEmpty()) {
	            for (Tag t : post.getTags()) {
	                t.setRecipeid(recipeId);
	            }
	            postDAO.insertTags(post.getTags());
	        }

	        // 3) ingredients (batch)
	        if (post.getIngredients() != null && !post.getIngredients().isEmpty()) {
	            for (int i = 0; i < post.getIngredients().size(); i++) {
	                Ingredients ing = post.getIngredients().get(i);
	                ing.setRecipeid(recipeId);
	                ing.setIngorder(i + 1); // 반드시 순서 지정
	            }
	            postDAO.insertIngredients(post.getIngredients());
	        }

	        // 4) sequences: insert each individually to get generated recipestepid
	        List<Attachment> attachmentsToInsert = new ArrayList<>();
	        if (post.getSequences() != null && !post.getSequences().isEmpty()) {
	            for (PostSequence seq : post.getSequences()) {
	                seq.setRecipeid(recipeId);
	                postDAO.insertSequence(seq);
	                int stepId = seq.getRecipestepid();

	                if (seq.getAttachments() != null && !seq.getAttachments().isEmpty()) {
	                    for (Attachment a : seq.getAttachments()) {
	                        a.setRecipestepid(stepId);
	                    }
	                    postDAO.insertseqAttachments(seq.getAttachments()); // 여기서만 insert
	                }
	            }
	        }

	        // 5) top-level attachments만 모음
	        if (post.getAttachments() != null && !post.getAttachments().isEmpty()) {
	            for (Attachment a : post.getAttachments()) {
	                a.setRecipeid(recipeId);
	                attachmentsToInsert.add(a);
	            }
	        }

	        // 6) insertAttachments는 recipe-level만 insert
	        if (!attachmentsToInsert.isEmpty()) {
	            postDAO.insertAttachments(attachmentsToInsert);
	        }
	    }

	    @Override
	    public List<Post> getAllPosts() {
	        return postDAO.getAllPosts();
	    }	

	    @Override
	    public Post getPostById(int recipeid, String currentUserId, String accessLevel) {
	        Map<String,Object> params = new HashMap<>();
	        params.put("recipeid", recipeid);
	        params.put("currentUserId", currentUserId);
	        params.put("accessLevel", accessLevel);
	        return postDAO.getPostById(params);
	    }

	    @Override
	    @Transactional
	    public void deactivatePost(int recipeid) {
	        postDAO.deactivatePost(recipeid);
	    }

	    @Override
	    public List<CategoryItem> listCategoryItems() {
	        return postDAO.listCategoryItems();
	    }

	    @Override
	    public List<CategoryOption> listCategoryOptionsByItemId(int itemid) {
	        return postDAO.listCategoryOptionsByItemId(itemid);
	    }

		@Override
		public void insertSequence(PostSequence seq) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void insertAttachments(List<Attachment> attList) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public List<Post> postlist(Pager pager) {
			//int total = postDAO.posttotal(pager);
			
			//pager.setTotal(total);
			
			return postDAO.postlist(pager);
		}

		@Override
		@Transactional
		public void dummy(Users users) {
			// TODO Auto-generated method stub
			for(int i=0; i < 100; i++) {
				Post item = new Post();
				
				item.setUserid(users.getUserid());
				item.setRecipetitle("게시글명 " + i);
				item.setRecipecontent("어쩌구저쩌구 " + i);
				item.setTypeid(1);
				item.setSituationid(5);
				item.setMethodid(9);
				item.setPeopleid(13);
				item.setTimeid(17);
				item.setDifficultyid(21);
				
				postDAO.insertPost(item);
			}
		}
		
		@Override
		public void init() {
			
		}

		@Override
	    public Post getPostDetail(int recipeid) {
			
	        return postDAO.getPostDetail(recipeid);
	        
	    }
		
}