package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.Author;

public interface AuthorService {
	
	List<Author>findAllAuthors();
	Author findAuthorById(Integer id);
	Author saveAuthor(Author author);
	Author replaceAuthorById(Author author, Integer id);
	Author updateAuthorById(Author author, Integer id);
	void deleteAuthorById(Integer id);
	
	
	
	

}
