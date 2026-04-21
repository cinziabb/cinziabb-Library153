package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.Book;

public interface BookService {
	
	List<Book> findAllBooks();
	Book findBookById(Integer id);
	Book saveBook(Book book);
	Book updateBookById(Book book, Integer id);
	void deleteBookById(Integer id);
}
