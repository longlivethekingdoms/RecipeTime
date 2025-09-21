package com.recipetime.find.Service;

import java.util.List;

import com.recipetime.find.Model.*;

public interface PostService {
	void insertPost(Post post);
    List<Post> getAllPosts();
    Post getPostById(int recipeid, String currentUserId, String accessLevel);
    void deactivatePost(int recipeid);

    List<CategoryItem> listCategoryItems();
    List<CategoryOption> listCategoryOptionsByItemId(int itemid);

	void insertSequence(PostSequence seq);
	void insertAttachments(List<Attachment> attachments);
	
}