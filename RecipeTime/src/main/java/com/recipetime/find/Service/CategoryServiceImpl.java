package com.recipetime.find.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipetime.find.DAO.CategoryDAO;
import com.recipetime.find.Model.CategoryOption;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
    CategoryDAO dao;
	
	@Override
	public List<CategoryOption> getOptionsByItemId(int itemid) {
		return dao.getOptionsByItemId(itemid);
	}

}
