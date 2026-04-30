package com.generation153.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
	

	List<Author> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);
	
	List<Author> findByLastNameIgnoreCase(String lastName);
	
	List<Author> findByLastNameStartingWith(String lastName);
	
// <<<<<<< HEAD
//	 List<Author> findByLastameIgnoreCase(String lastName);
	
//	List<Author> findByLastnameStartingWith(String lastName); 
// =======
	
// >>>>>>> refs/remotes/origin/develop
	
}
