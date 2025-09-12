package com.recipetime.find.DAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.recipetime.find.Model.Post;

@Repository
public class PostDAOImpl implements PostDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void insertPost(Post post) {
		sqlSession.insert("posts.insertPost", post);
	}

	@Override
	public void insertTags(Post post) {
		if(post.getTags() != null) {
			post.getTags().forEach(tag -> sqlSession.insert("posts.insertTag", tag));
		}
	}

	@Override
	public void insertAttachments(Post post) {
		if(post.getAttachments() != null) {
			post.getAttachments().forEach(att -> sqlSession.insert("posts.insertAttachment", att));
		}
	}

	@Override
	public void insertSequences(Post post) {
		if(post.getSequences() != null) {
			post.getSequences().forEach(seq -> sqlSession.insert("posts.insertSequence", seq));
		}
	}

	@Override
	public List<Post> getAllPosts() {
		return sqlSession.selectList("posts.getAllPosts");
	}

	@Override
	public Post getPostById(int recipeid) {
		return sqlSession.selectOne("posts.getPostById", recipeid);
	}

}
