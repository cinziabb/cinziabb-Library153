package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.Category;

public interface CategoryService {
	
	List<Category> findAllCategories();
	Category findCategoryById(Integer id);
	Category saveCategory(Category category);
	Category replaceCategoryById(Category category, Integer id);
	Category updateCategoyById(Category category, Integer id);
	void deleteCategoryById(Integer id);
	

}
