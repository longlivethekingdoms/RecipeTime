package com.recipetime.find.Service;

import java.util.List;

import com.recipetime.find.Model.CategoryOption;

public interface CategoryService {
	
	List<CategoryOption> getOptionsByItemId(int itemid);

}
