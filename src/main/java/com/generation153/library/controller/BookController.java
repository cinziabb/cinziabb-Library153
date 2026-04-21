package com.generation153.library.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation153.library.entity.Book;
import com.generation153.library.service.BookService;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

	// Dependency injection
	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	// API: GET + http://localhost:8080/api/v1/books 
	@GetMapping()
	public List<Book> getAllBooks() {
		return bookService.findAllBooks();
	}

	// API: GET + http://localhost:8080/api/v1/books/1 
	@GetMapping("/{id}")
	public Book getBookById(@PathVariable Integer id) {
		return bookService.findBookById(id);
	}
}
