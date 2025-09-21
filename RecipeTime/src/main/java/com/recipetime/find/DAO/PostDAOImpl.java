package com.recipetime.find.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.recipetime.find.Model.Attachment;
import com.recipetime.find.Model.CategoryItem;
import com.recipetime.find.Model.CategoryOption;
import com.recipetime.find.Model.Ingredients;
import com.recipetime.find.Model.Post;
import com.recipetime.find.Model.PostSequence;
import com.recipetime.find.Model.Tag;

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
	public void insertTags(List<Tag> tags) {
		if(tags == null || tags.isEmpty()) return;
        sqlSession.insert(namespace + "insertTags", tags);
	}

	@Override
	public void insertIngredients(List<Ingredients> ingredients) {
		if(ingredients == null || ingredients.isEmpty()) return;
        sqlSession.insert(namespace + "insertIngredients", ingredients);		
	}
	
	@Override
    public void insertSequence(PostSequence seq) {
        sqlSession.insert(namespace + "insertSequence", seq);
    }


	@Override
    public void insertSequences(List<PostSequence> sequences) {
        if(sequences == null || sequences.isEmpty()) return;
        sqlSession.insert(namespace + "insertSequences", sequences);
    }

    @Override
    public void insertAttachments(List<Attachment> attachments) {
        if(attachments == null || attachments.isEmpty()) return;
        sqlSession.insert(namespace + "insertAttachments", attachments);
    }

    @Override
    public List<Post> getAllPosts() {
        return sqlSession.selectList(namespace + "getAllPosts");
    }

    @Override
    public Post getPostById(Map<String, Object> params) {
        return sqlSession.selectOne(namespace + "getPostById", params);
    }

    @Override
    public void deactivatePost(int recipeid) {
        sqlSession.update(namespace + "deactivatePost", recipeid);
    }

    @Override
    public List<CategoryItem> listCategoryItems() {
        return sqlSession.selectList(namespace + "listCategoryItems");
    }

    @Override
    public List<CategoryOption> listCategoryOptionsByItemId(int itemid) {
        return sqlSession.selectList(namespace + "listCategoryOptionsByItemId", itemid);
    }
}