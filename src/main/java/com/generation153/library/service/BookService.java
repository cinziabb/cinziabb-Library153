package com.generation153.library.service;

import java.util.List;

import com.generation153.library.dto.BookCreateDTO;
import com.generation153.library.dto.BookResponseDTO;
import com.generation153.library.dto.BookUpdateDTO;
import com.generation153.library.entity.Author;
import com.generation153.library.entity.Book;
import com.generation153.library.entity.Category;
import com.generation153.library.entity.Publisher;

public interface BookService {

	List<Book> findAllBooks();
	Book findBookById(Integer id);
	Book findBookByIsbn(String isbn);
	Book saveBook(Book book);
	Book replaceBookById(Book book, Integer id); //PUT
	Book updateBookById(Book book, Integer id); //PATCH
	void deleteBookById(Integer id);
	
	List<Book> findBooksByTitle(String title);
	
	List<Book> findBooksByAuthorId(Author author);
	List<Book> findBooksByCategoryId(Category category);
	List<Book> findBooksByPublisherId(Publisher publisher);
	
	List<Book> findBooksByAuthorName(String firstName, String lastName);
	List<Book> findBooksByCategoryName(String name);
	List<Book> findBooksByPublisherName(String name);
	

	// Mapping BookCreateDTO --> Book
	Book mapToEntity(BookCreateDTO dto);
	Book mapToEntity(BookUpdateDTO dto);
	
	// Mapping Book --> BookResponseDTO
	BookResponseDTO mapToResponseDTO(Book book);
	
	// Mapping List<Book> --> List<BookResponseDTO>
	List<BookResponseDTO> mapToListResponseDTO(List<Book> books);
	
}
