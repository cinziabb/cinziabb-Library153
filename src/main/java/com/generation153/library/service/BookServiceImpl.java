package com.generation153.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.generation153.library.entity.Book;
import com.generation153.library.exception.DuplicatedResourceException;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.BookRepository;


@Service
public class BookServiceImpl implements BookService {
	
	// Dependency Injection
	private final BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book findBookById(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("id nullo");
		}
		Optional<Book> optBook = bookRepository.findById(id);
//		if (optBook.isEmpty()) {
//			throw new NotFoundException("libro non trovato con id: " + id);
//		}
//		return optBook.get();
		return optBook
				.orElseThrow(() -> new NotFoundException("libro non trovato con id: " + id));
	}

	@Override
	public Book saveBook(Book book) {
		if (book == null) {
			throw new IllegalArgumentException("book nullo");
		}
		if (bookRepository.existsByIsbn(book.getIsbn())) {
			throw new DuplicatedResourceException("book esistente con isbn: " + book.getIsbn());
		}
		return bookRepository.save(book);
	}

	@Override
	public Book updateBookById(Book book, Integer id) {
		if (book == null) {
			throw new IllegalArgumentException("book nullo");
		}
		if (id == null) {
			throw new IllegalArgumentException("id nullo");
		}
		Optional<Book> optBook = bookRepository.findById(id);
		if (optBook.isEmpty()) {
			throw new NotFoundException("libro non trovato con id: " + id);
		}
		Book savedBook = optBook.get();
		if (book.getIsbn() != null) {
			if (book.getIsbn() != savedBook.getIsbn() && !bookRepository.existsByIsbn(book.getIsbn())) {
				savedBook.setIsbn(book.getIsbn());
			}
		}
		if (book.getTitle() != null && !book.getTitle().isBlank()) {
			savedBook.setTitle(book.getTitle());
		}
		if (book.getLanguage() != null && !book.getLanguage().isBlank()) {
			savedBook.setLanguage(book.getLanguage());
		}
		if (book.getImageUri() != null && !book.getImageUri().isBlank()) {
			savedBook.setImageUri(book.getImageUri());
		}
		if (book.getEdition() > 0) {
			savedBook.setEdition(book.getEdition());
		}
		savedBook.setLendable(book.isLendable());
		
		return bookRepository.save(savedBook);
	}

	@Override
	public void deleteBookById(Integer id) {
		Optional<Book> optBook = bookRepository.findById(id);
		if (optBook.isEmpty()) {
			throw new NotFoundException("libro non trovato con id: " + id);
		}
		bookRepository.delete(optBook.get());
		
	}

}
