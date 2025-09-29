package com.recipetime.find.Service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.recipetime.find.Model.*;
import com.recipetime.find.pager.Pager;

public interface PostService {
	void insertPost(Post post);
    List<Post> getAllPosts();
    
    Post getPostById(int recipeid, String currentUserId, String accessLevel);
    void deactivatePost(int recipeid);

    List<CategoryItem> listCategoryItems();
    List<CategoryOption> listCategoryOptionsByItemId(int itemid);

	void insertSequence(PostSequence seq);
	void insertAttachments(List<Attachment> attachments);
	
	List<Post> postlist(Pager pager);
	
	void dummy(Users loginUser);
	void init();
	
	Post getPostDetail(int recipeid);
}