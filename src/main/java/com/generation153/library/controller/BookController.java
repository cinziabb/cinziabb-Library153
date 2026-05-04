package com.generation153.library.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generation153.library.dto.BookCreateDTO;
import com.generation153.library.dto.BookResponseDTO;
import com.generation153.library.dto.BookUpdateDTO;
import com.generation153.library.entity.Author;
import com.generation153.library.entity.Book;
import com.generation153.library.service.BookService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Validated //consente di validare i parametri del controller come @PathVariable e @RequestParam
public class BookController {

	// Dependency injection
	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	// API: GET + http://localhost:8080/api/v1/public/books 
	@GetMapping("/public/books")
	public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
		List<Book> books = bookService.findAllBooks();
		// mapping (MANUALE) entity --> dto
		List<BookResponseDTO> responseDTOs = bookService.mapToListResponseDTO(books);
		return ResponseEntity.ok(responseDTOs);
	}

	// API: GET + http://localhost:8080/api/v1/public/books/isbn 
	@GetMapping("/public/books/{isbn}")
	public ResponseEntity<BookResponseDTO> getBookByIsbn(
			@NotBlank(message = "isbn non può essere nullo o vuoto") 
			@PathVariable String isbn) {
		Book book = bookService.findBookByIsbn(isbn);
		// mapping (MANUALE) entity --> dto
		BookResponseDTO dto = bookService.mapToResponseDTO(book);
		return ResponseEntity.ok(dto);
	}

	// API: GET + http://localhost:8080/api/v1/public/books/title?title=title
	@GetMapping("/public/books/title")
	public ResponseEntity<List<BookResponseDTO>> getBooksByTitle(
			@NotBlank(message = "titolo nullo o vuoto") 
			@RequestParam String title) {
		List<Book> books = bookService.findBooksByTitle(title);
		// mapping (MANUALE) entity --> dto
		List<BookResponseDTO> responseDTOs = bookService.mapToListResponseDTO(books);
		return ResponseEntity.ok(responseDTOs);
	}

	// API: GET + http://localhost:8080/api/v1/public/books/author?firstname=firstname&lastname=lastname
	@GetMapping("/public/books/author")
	public ResponseEntity<List<BookResponseDTO>> getBooksByAuthor(
			@NotBlank(message = "first name nullo o vuoto") 
			@RequestParam String firstname,
			@NotBlank(message = "last name nullo o vuoto") 
			@RequestParam String lastname) {
		List<Book> books = bookService.findBooksByAuthorName(firstname, lastname);
		System.err.println(books);
		// mapping (MANUALE) entity --> dto
		List<BookResponseDTO> responseDTOs = bookService.mapToListResponseDTO(books);
		return ResponseEntity.ok(responseDTOs);
	}

	// API: GET + http://localhost:8080/api/v1/public/books/category?name=name
	@GetMapping("/public/books/category")
	public ResponseEntity<List<BookResponseDTO>> getBooksByCategory(
			@NotBlank(message = "name nullo o vuoto") 
			@RequestParam String name) {
		List<Book> books = bookService.findBooksByCategoryName(name);
		// mapping (MANUALE) entity --> dto
		List<BookResponseDTO> responseDTOs = bookService.mapToListResponseDTO(books);
		return ResponseEntity.ok(responseDTOs);
	}

	// API: GET + http://localhost:8080/api/v1/public/books/publisher?name=name
	@GetMapping("/public/books/publisher")
	public ResponseEntity<List<BookResponseDTO>> getBooksByPublisher(
			@NotBlank(message = "name nullo o vuoto") 
			@RequestParam String name) {
		List<Book> books = bookService.findBooksByPublisherName(name);
		// mapping (MANUALE) entity --> dto
		List<BookResponseDTO> responseDTOs = bookService.mapToListResponseDTO(books);
		return ResponseEntity.ok(responseDTOs);
	}

	// API: GET + http://localhost:8080/api/v1/admin/books/id 
	@GetMapping("/admin/books/{id}")
	public ResponseEntity<BookResponseDTO> getBookById(
			@Min(value = 1, message = "L'ID deve essere almeno 1") 
			@PathVariable Integer id) {
		Book book = bookService.findBookById(id);
		// mapping (MANUALE) entity --> dto
		BookResponseDTO dto = bookService.mapToResponseDTO(book);
		return ResponseEntity.ok(dto);
	}


	// API: POST + http://localhost:8080/api/v1/admin/books 
	@PostMapping("/admin/books")
	public ResponseEntity<BookResponseDTO> createBook(
			@Valid @RequestBody BookCreateDTO dto) {
		// mapping (MANUALE) dto --> entity
		Book book = bookService.mapToEntity(dto);
		Book createdBook = bookService.saveBook(book);
		// mapping (MANUALE) entity --> dto
		BookResponseDTO responseDTO = bookService.mapToResponseDTO(createdBook);
		return ResponseEntity.created(URI
				.create("http://localhost:8080/admin/books/" + createdBook.getId()))
				.body(responseDTO);
		/*
		 * Alternativa:
		 * return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
		 * 
		 */
	}

	// API: PUT + http://localhost:8080/api/v1/admin/books/id
	@PutMapping("/admin/books/{id}")
	public ResponseEntity<BookResponseDTO> replaceBookById(
			@Valid @RequestBody BookCreateDTO dto,
			@Min(value = 1, message = "L'ID deve essere almeno 1")
			@PathVariable Integer id) {
		// mapping (MANUALE) dto --> entity
		Book book = bookService.mapToEntity(dto);
		Book replacedBook = bookService.replaceBookById(book, id);
		// mapping (MANUALE) entity --> dto
		BookResponseDTO responseDTO = bookService.mapToResponseDTO(replacedBook);
		return ResponseEntity.ok(responseDTO);
	}

	// API: PATCH + http://localhost:8080/api/v1/admin/books/id
	@PatchMapping("/admin/books/{id}")
	public ResponseEntity<BookResponseDTO> updateBookById(
			@Valid @RequestBody BookUpdateDTO dto,
			@Min(value = 1, message = "L'ID deve essere almeno 1")
			@PathVariable Integer id) {
		// mapping (MANUALE) dto --> entity
		Book book = bookService.mapToEntity(dto);
		Book updatedBook = bookService.updateBookById(book, id);
		// mapping (MANUALE) entity --> dto
		BookResponseDTO responseDTO = bookService.mapToResponseDTO(updatedBook);
		return ResponseEntity.ok(responseDTO);
	}

	// API: DELETE + http://localhost:8080/api/v1/admin/books/id
	@DeleteMapping("/admin/books/{id}")
	public ResponseEntity<String> deleteBook(
			@Min(value = 1, message = "L'ID deve essere almeno 1") 
			@PathVariable Integer id) {
		bookService.deleteBookById(id);
		return ResponseEntity.ok("Libro eliminato con successo");
	}
}
