package com.generation153.library.service;

import java.util.List;
import java.util.Optional;

import com.generation153.library.entity.Author;
import com.generation153.library.exception.DuplicatedResourceException;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.AuthorRepository;


public class AuthorServiceImpl implements AuthorService{

	private final AuthorRepository authorRepository;

	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public List<Author> finAllAuthor() {
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
	public void deleteAuthorById(Integer id) {
		// TODO Auto-generated method stub
		
	}
	}
