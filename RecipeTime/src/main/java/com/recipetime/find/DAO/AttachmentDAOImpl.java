package com.recipetime.find.DAO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.recipetime.find.Model.Attachment;

@Repository
public class AttachmentDAOImpl implements AttachmentDAO {
	
	private static final String namespace = "attachmentMapper";
	
	@Autowired
	private SqlSession sqlSession;

	
	@Override
	public void insertAttachment(Attachment attachment) {
		sqlSession.insert(namespace + "insertAttachment", attachment);		
	}

	@Override
	public void deleteAttachment(Attachment attachment) {
		sqlSession.delete(namespace + "deleteAttachment", attachment);
	}

}
