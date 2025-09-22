package com.recipetime.find.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.recipetime.find.DAO.PostDAO;
import com.recipetime.find.Model.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDAO postDAO;

    @Override
    @Transactional
    public void insertPost(Post post) {
        // 1) recipepost insert -> post.recipeid Ã¤¿öÁü (useGeneratedKeys in mapper)
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
            for (Ingredients ing : post.getIngredients()) {
                ing.setRecipeid(recipeId);
            }
            postDAO.insertIngredients(post.getIngredients());
        }

        // 4) sequences: insert each individually to get generated recipestepid
        List<Attachment> attachmentsToInsert = new ArrayList<>();
        if (post.getSequences() != null && !post.getSequences().isEmpty()) {
            for (PostSequence seq : post.getSequences()) {
                seq.setRecipeid(recipeId);
                postDAO.insertSequence(seq); // useGeneratedKeys -> seq.recipestepid set
                int stepId = seq.getRecipestepid();

                // if this sequence had attachments (controller already created Attachment objs)
                if (seq.getAttachments() != null && !seq.getAttachments().isEmpty()) {
                    for (Attachment a : seq.getAttachments()) {
                        a.setRecipeid(recipeId); // also set recipeid for row
                        a.setRecipestepid(stepId);
                        attachmentsToInsert.add(a);
                    }
                }
            }
        }

        // 5) top-level attachments (post.getAttachments())
        if (post.getAttachments() != null && !post.getAttachments().isEmpty()) {
            for (Attachment a : post.getAttachments()) {
                a.setRecipeid(recipeId); // recipe-level attachments
                // recipestepid remains null
                attachmentsToInsert.add(a);
            }
        }

        // 6) insert all gathered attachments in batch
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
}