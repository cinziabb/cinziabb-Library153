package com.generation153.library.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation153.library.dto.BookCreateDTO;
import com.generation153.library.entity.Book;
import com.generation153.library.service.BookService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1")
public class BookController {

	// Dependency injection
	private final BookService bookService;
	private final ModelMapper modelMapper;

	public BookController(BookService bookService, ModelMapper modelMapper) {
		this.bookService = bookService;
		this.modelMapper= modelMapper;
	}

	// API: GET + http://localhost:8080/api/v1/public/books 
	@GetMapping("/public/books")
	public ResponseEntity<List<Book>> getAllBooks() {
		return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
	}
	
	//In alternativa
//	@GetMapping()
//	public ResponseEntity<List<Book>> getAllBooks() {
//		return ResponseEntity.ok(findAllBooks());
//	}

	// API: GET + http://localhost:8080/api/v1/admin/books/1 
	@GetMapping("/admin/books/{id}")
	public ResponseEntity<Book> getBookById(@Min(1) @PathVariable Integer id) {
		return new ResponseEntity<>(bookService.findBookById(id), HttpStatus.OK);
	}
	
	// API: POST + http://localhost:8080/api/v1/admin/books 
	@PostMapping("/admin/books")
	public ResponseEntity<Book> createBook(@Valid @RequestBody BookCreateDTO dto) {
		
		// mapping dto --> entity
		Book book = modelMapper.map(dto, Book.class);
		return new ResponseEntity<Book>(bookService.saveBook(book), HttpStatus.CREATED);
		
	}
	
	
	
}
