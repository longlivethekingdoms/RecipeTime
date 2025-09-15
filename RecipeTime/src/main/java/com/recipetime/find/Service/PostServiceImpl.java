package com.recipetime.find.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipetime.find.DAO.PostDAO;
import com.recipetime.find.Model.Post;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDAO postDAO;

    @Override
    @Transactional
    public void insertPost(Post post) {
        postDAO.insertPost(post);

        if (post.getTags() != null && !post.getTags().isEmpty()) {
            postDAO.insertTags(post);
        }
        if (post.getAttachments() != null && !post.getAttachments().isEmpty()) {
            postDAO.insertAttachments(post);
        }
        if (post.getSequences() != null && !post.getSequences().isEmpty()) {
            postDAO.insertSequences(post);
        }
    }

    @Override
    public List<Post> getAllPosts() {
        return postDAO.getAllPosts();
    }

    @Override
    public Post getPostById(int recipeid, String currentUserId, boolean isAdmin) {
        return postDAO.getPostById(recipeid, currentUserId, isAdmin);
    }
    
    @Override
    @Transactional
    public void deactivatePost(int recipeid) {
        postDAO.deactivatePost(recipeid);
    }
}
