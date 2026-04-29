package com.generation153.library.service;


//<<<<<<< HEAD
import java.util.List;  
//=======
//import java.util.List;
//>>>>>>> refs/remotes/origin/develop
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.generation153.library.entity.Category;
import com.generation153.library.exception.DuplicatedResourceException;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.CategoryRepository;
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;



	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> findAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findCategoryById(Integer id) {

		if(id == null) {
			throw new IllegalArgumentException("Id nullo");
		}

		Optional<Category> categoryOpt = categoryRepository.findById(id);

		return categoryOpt.orElseThrow(()-> new NotFoundException("Categoria non trovata"));

	}

	@Override
	public Category saveCategory(Category category) {

		if(category == null) {
			throw new IllegalArgumentException("Categoria nulla");
		}

		List<Category> categoriesList = categoryRepository.findAll();

		//		Set<Category> categories = new HashSet<Category>();
		//		
		//		for(Category c : categoriesList) {
		//			categories.add(c);	
		//		}

		for(Category c : categoriesList) {
			if(c.equals(category)) {
				throw new DuplicatedResourceException("La categoria è già esistente");
			}
		}

		return categoryRepository.save(category);
	}

	@Override
	public Category replaceCategoryById(Category category, Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("Id nullo");
		}

		if(category == null) {
			throw new IllegalArgumentException("Categoria nulla");
		}

		Category categoryOpt = categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Categoria non trovata"));

		categoryOpt.setName(category.getName());

		return categoryRepository.save(categoryOpt);
	}

	@Override
	public Category updateCategoyById(Category category, Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("Id nullo");
		}
		if(category == null) {
			throw new IllegalArgumentException("Categoria nulla");
		}
		Category categoryOpt = categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Categoria non trovata"));
		if(category.getName() != null && !category.getName().isBlank()) {
			categoryOpt.setName(category.getName());
		}
		return categoryRepository.save(categoryOpt);
	}

	@Override
	public void deleteCategoryById(Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("Id nullo");
		}
		Category categoryOpt = categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Categoria non trovata"));
		categoryRepository.delete(categoryOpt);
	}

}
