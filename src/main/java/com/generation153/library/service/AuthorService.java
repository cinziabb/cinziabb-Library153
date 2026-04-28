package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.Author;

public interface AuthorService {
	List<Author>finAllAuthor();
	Author saveAuthor (Author author);
	Author findAuthorById(Author author, Integer id);
	Author replaceAuthorById(Author author, Integer id);
	Author updateAuthorById(Author author, Integer id);
	
}
