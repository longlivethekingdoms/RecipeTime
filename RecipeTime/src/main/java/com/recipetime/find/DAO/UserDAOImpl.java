package com.recipetime.find.DAO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.recipetime.find.Model.Users;

public class UserDAOImpl implements UserDAO {

	@Autowired
	SqlSession sqlSession;
	
	private static final String namespace = "users";
	
	@Override
	public void insertUserAdmin(Users users) {
		sqlSession.insert(namespace + ".addAdmin", users);
	}
	
	@Override
	public int duplicateIDCheck(String userid) {	
		return sqlSession.selectOne(namespace + ".duplicateIDCheck", userid);
	}

	@Override
	public int duplicateNickname(String nickname) {
		return sqlSession.selectOne(namespace + ".duplicateNickname", nickname);
	}

	@Override
	public int duplicateEmail(String useremail) {
		return sqlSession.selectOne(namespace + ".duplicateEmail", useremail);
	}

	@Override
	public void insertJoin(Users users) {
		sqlSession.insert(namespace + ".addUser", users);
	}

}
