package com.recipetime.find.DAO;

import java.util.List;

import com.recipetime.find.Model.CategoryOption;

public interface CategoryDAO {
	List<CategoryOption> getOptionsByItemId(int itemid);
}
