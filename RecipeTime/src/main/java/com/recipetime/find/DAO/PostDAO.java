package com.recipetime.find.DAO;

import java.util.List;

import com.recipetime.find.Model.Post;

public interface PostDAO {
	void insertPost(Post post);
	void insertTags(Post post);
	void insertAttachments(Post post);
	void insertSequences(Post post);
	
	List<Post> getAllPosts();
	Post getPostById(int recipeid);
}
