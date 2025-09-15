package com.recipetime.find.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.recipetime.find.Model.Post;

@Repository
public class PostDAOImpl implements PostDAO {

    private static final String namespace = "postMapper.";

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void insertPost(Post post) {
        sqlSession.insert(namespace + "insertPost", post);
    }

    @Override
    public void insertTags(Post post) {
        sqlSession.insert(namespace + "insertTags", post);
    }

    @Override
    public void insertAttachments(Post post) {
        sqlSession.insert(namespace + "insertAttachments", post);
    }

    @Override
    public void insertSequences(Post post) {
        sqlSession.insert(namespace + "insertSequences", post);
    }

    @Override
    public List<Post> getAllPosts() {
        return sqlSession.selectList(namespace + "getAllPosts");
    }

    @Override
    public Post getPostById(int recipeid, String currentUserId, boolean isAdmin) {
        Map<String, Object> params = new HashMap<>();
        params.put("recipeid", recipeid);
        params.put("currentUserId", currentUserId);
        params.put("isAdmin", isAdmin);

        return sqlSession.selectOne(namespace + "getPostById", params);
    }
    
    @Override
    public void deactivatePost(int recipeid) {
        sqlSession.update("post.deactivatePost", recipeid);
    }
}