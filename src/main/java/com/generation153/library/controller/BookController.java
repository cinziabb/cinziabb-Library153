package com.generation153.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<Book>> getAllBooks() {
		return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
	}
	
	//In alternativa
//	@GetMapping()
//	public ResponseEntity<List<Book>> getAllBooks() {
//		return ResponseEntity.ok(findAllBooks());
//	}

	// API: GET + http://localhost:8080/api/v1/books/1 
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
		return new ResponseEntity<>(bookService.findBookById(id), HttpStatus.OK);
	}
	
	
	
}
