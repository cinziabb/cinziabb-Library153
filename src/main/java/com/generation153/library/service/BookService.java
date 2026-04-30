package com.generation153.library.service;

import java.util.List;

import com.generation153.library.dto.BookCreateDTO;
import com.generation153.library.entity.Author;
import com.generation153.library.entity.Book;
import com.generation153.library.entity.Category;

public interface BookService {
	
	List<Book> findAllBooks();
	Book findBookById(Integer id);
	List<Book> findBooksByTitle(String title);
	Book saveBook(Book book);
	Book replaceBookById(Book book, Integer id); //PUT
	Book updateBookById(Book book, Integer id); //PATCH
	void deleteBookById(Integer id);
	List<Book> findBooksByAuthor(Author author);
	List<Book> findBooksByCategory(Category category);
	BookCreateDTO mapToDTO(Book book);
	Book mapToEntity(BookCreateDTO dto);
}
