package com.recipetime.find.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.recipetime.find.DAO.PostDAO;
import com.recipetime.find.Model.Post;

public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDAO;
	
	@Override
	@Transactional
	public void insertPost(Post post) {
		postDAO.insertPost(post);
		postDAO.insertTags(post);
		postDAO.insertAttachments(post);
		postDAO.insertSequences(post);
	}

	@Override
	public List<Post> getAllPosts() {
		return postDAO.getAllPosts();	
	}

	@Override
	public Post getPostById(int recipeid) {
		return postDAO.getPostById(recipeid);
	}

}
