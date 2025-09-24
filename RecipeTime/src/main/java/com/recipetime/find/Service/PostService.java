package com.recipetime.find.Service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.recipetime.find.Model.*;

public interface PostService {
	void insertPost(Post post);
    List<Post> getAllPosts();
    List<Tag> getTagsByRecipeId(int recipeid);
    List<Map<String, Object>> getCategoryItems();
    List<Map<String, Object>> getCategoryOptionsByItem(int itemid);
    int getRecipeCount(List<Integer> categoryOptions);
    List<Post> getRecipeList(List<Integer> categoryOptions, int offset, int size);
    Post getPostById(int recipeid, String currentUserId, String accessLevel);
    void deactivatePost(int recipeid);

    List<CategoryItem> listCategoryItems();
    List<CategoryOption> listCategoryOptionsByItemId(int itemid);

	void insertSequence(PostSequence seq);
	void insertAttachments(List<Attachment> attachments);
}