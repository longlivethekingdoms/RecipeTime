package com.recipetime.find.DAO;

import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.recipetime.find.Model.PostSequence;

@Repository
public class SequenceDAOImpl implements SequenceDAO {

	private static final String namespace = "sequencesMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void insertSequence(PostSequence sequences) {
		System.out.println(namespace + "insertSequences");
		sqlSession.insert(namespace + "insertSequences", sequences);
		System.out.println("?");
	}

	@Override
	public void updateSequence(PostSequence sequences) {
		sqlSession.update(namespace + "updateSequences", sequences);
	}

	@Override
	public void deleteSequence(PostSequence sequences) {
		sqlSession.delete(namespace + "deleteSequences", sequences);
	}

}
