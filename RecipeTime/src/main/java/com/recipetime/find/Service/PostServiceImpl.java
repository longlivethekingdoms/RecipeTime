package com.recipetime.find.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipetime.find.DAO.PostDAO;
import com.recipetime.find.Model.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDAO postDAO;

    @Override
    @Transactional
    public void insertPost(Post post) {
        // 1) �Խñ� ���� insert (mapper���� keyProperty="recipeid"�� ����Ű ����)
        postDAO.insertPost(post);

        // 2) �±� insert (mapper�� ����Ʈ�� �޾� ó��)
        if (post.getTags() != null && !post.getTags().isEmpty()) {
            // �±׿� recipeid ����
            int order = 1;
            for (Tag t : post.getTags()) {
                t.setRecipeid(post.getRecipeid());
                t.setTagorder(order++);
            }
            postDAO.insertTags(post.getTags());
        }

        // 3) ��� insert
        if (post.getIngredients() != null && !post.getIngredients().isEmpty()) {
            int idx = 1;
            for (Ingredients ing : post.getIngredients()) {
                ing.setRecipeid(post.getRecipeid());
                ing.setIngorder(idx++);
            }
            postDAO.insertIngredients(post.getIngredients());
        }

        // 4) ���� insert
        if (post.getSequences() != null && !post.getSequences().isEmpty()) {
            int seqNo = 1;
            for (PostSequence s : post.getSequences()) {
                s.setRecipeid(post.getRecipeid());
                s.setRecipestep(seqNo++);
            }
            postDAO.insertSequences(post.getSequences());
        }

        // 5) ÷������ insert
        if (post.getAttachments() != null && !post.getAttachments().isEmpty()) {
            int forder = 1;
            for (Attachment a : post.getAttachments()) {
                a.setRecipeid(post.getRecipeid());
                a.setFileorder(forder++);
            }
            postDAO.insertAttachments(post.getAttachments());
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
        params.put("isAdmin", accessLevel);
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
}