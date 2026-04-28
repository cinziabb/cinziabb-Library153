package com.generation153.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
	
	List<Author> findByFirstnameAndLastnameIgnoreCase(String firstNname, String lastName);
	
	List<Author> findByLastnameIgnoreCase(String lastName);
	
	List<Author> findByLastnameStartingWith(String lastName); 
	
}
