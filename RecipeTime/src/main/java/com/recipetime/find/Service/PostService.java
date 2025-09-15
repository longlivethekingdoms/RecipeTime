package com.recipetime.find.Service;

import java.util.List;

import com.recipetime.find.Model.Post;

public interface PostService {
    void insertPost(Post post);
    List<Post> getAllPosts();
    Post getPostById(int recipeid, String currentUserId, boolean isAdmin);
	void deactivatePost(int recipeid);
}
