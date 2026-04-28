package com.generation153.library.service;

import java.util.List;
import java.util.Optional;

import com.generation153.library.entity.Author;
import com.generation153.library.repository.AuthorRepository;

public class AuthorServiceImpl implements AuthorService {

	private final AuthorRepository authorRepository;
	
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public List<Author> finAllAuthor() {
		return authorRepository.findAll();
	}

//	@Override
//	public Author saveAuthor(Author author) {
//		if(id==null) {
//			throw new IllegalArgumentException("id" + "nullo");
//		}
//		Optional<Author> authors = authorRepository.findById(id);
//		return author.orElseThrow(() -> new NotFoundException ("autore con id" + id+ "non trovato"));
//	}
//
//	@Override
//	public Author findAuthorById(Author author, Integer id) {
//		if(id==null) {
//			throw new IllegalArgumentException("autore nullo");
//		} List<Author> authors
//		return null;
//	}

	@Override
	public Author replaceAuthorById(Author author, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Author updateAuthorById(Author author, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Author saveAuthor(Author author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Author findAuthorById(Author author, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
