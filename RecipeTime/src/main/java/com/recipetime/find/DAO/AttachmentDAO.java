package com.recipetime.find.DAO;

import com.recipetime.find.Model.Attachment;

public interface AttachmentDAO {
	void insertAttachment(Attachment attachment);
	
	void deleteAttachment(Attachment attachment);
}
