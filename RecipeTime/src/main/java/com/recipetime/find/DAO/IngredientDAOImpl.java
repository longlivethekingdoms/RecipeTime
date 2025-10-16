package com.recipetime.find.DAO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.recipetime.find.Model.Ingredients;

@Repository
public class IngredientDAOImpl implements IngredientDAO {
	
	private static final String namespace = "ingredientsMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void insertIngredient(Ingredients ingredients) {
		sqlSession.insert(namespace + "insertIngredients", ingredients);
	}

	@Override
	public void updateIngredient(Ingredients ingredients) {
		sqlSession.update(namespace + "updateIngredients", ingredients);
	}

	@Override
	public void deleteIngredient(Ingredients ingredients) {
		sqlSession.delete(namespace + "deleteIngredients", ingredients);
	}

}
