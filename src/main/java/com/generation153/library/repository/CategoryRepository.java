package com.generation153.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	List<Category>findByName(String name);
	List<Category>findByNameStartingWith(String name);
}