package com.recipetime.find.DAO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.recipetime.find.Model.Tag;

@Repository
public class TagDAOImpl implements TagDAO {

	private static final String namespace = "tagMapper.";

    @Autowired
    private SqlSession sqlSession;
	
	@Override
	public void insertTag(Tag tag) {
		sqlSession.insert(namespace + "insertTag", tag);
	}

	@Override
	public void updateTag(Tag tag) {
		sqlSession.update(namespace + "updateTag", tag);
	}

	@Override
	public void deleteTag(Tag tag) {
		sqlSession.delete(namespace + "deleteTag", tag);
	}
	
}
