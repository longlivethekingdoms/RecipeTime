package com.recipetime.find.DAO;

import com.recipetime.find.Model.Ingredients;

public interface IngredientDAO {
	void insertIngredient(Ingredients ingredients);
	
	void updateIngredient(Ingredients ingredients);
	
	void deleteIngredient(Ingredients ingredients);
}
