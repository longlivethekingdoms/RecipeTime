package com.recipetime.find.DAO;

import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.recipetime.find.Model.PostSequence;

public class SequenceDAOImpl implements SequenceDAO {

	private static final String namespace = "sequencesMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void insertSequence(PostSequence sequences) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSequence(PostSequence sequences) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSequence(PostSequence sequences) {
		// TODO Auto-generated method stub

	}

}
