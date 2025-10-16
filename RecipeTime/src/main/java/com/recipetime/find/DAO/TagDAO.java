package com.recipetime.find.DAO;

import java.util.List;

import com.recipetime.find.Model.Tag;

public interface TagDAO {
	void insertTag(Tag tag);
	
	void updateTag(Tag tag);
	
	void deleteTag(Tag tag);
}
