package com.recipetime.find.DAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.recipetime.find.Model.CategoryOption;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
    SqlSession sql;

    private final String namespace = "category.";

    @Override
    public List<CategoryOption> getOptionsByItemId(int itemid) {
        return sql.selectList(namespace + "getOptionsByItemId", itemid);
    }

}
