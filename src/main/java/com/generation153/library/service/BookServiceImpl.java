package com.generation153.library.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.generation153.library.entity.Author;
import com.generation153.library.entity.Book;
import com.generation153.library.entity.Category;
import com.generation153.library.entity.Publisher;
import com.generation153.library.exception.DuplicatedResourceException;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.AuthorRepository;
import com.generation153.library.repository.BookRepository;
import com.generation153.library.repository.CategoryRepository;
import com.generation153.library.repository.PublisherRepository;


@Service
public class BookServiceImpl implements BookService {

	// Dependency Injection
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final CategoryRepository categoryRepository;
	private final PublisherRepository publisherRepository;

	public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
			CategoryRepository categoryRepository, PublisherRepository publisherRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.categoryRepository = categoryRepository;
		this.publisherRepository = publisherRepository;
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

	/*
	 * Il parametro Book è già stato validato nel controller.
	 * Degli autori e della categoria contiene solo gli id.
	 */
	@Override
	public Book saveBook(Book book) {

		if (book == null) {
			throw new IllegalArgumentException("book nullo");
		}

		if (bookRepository.existsByIsbn(book.getIsbn())) {
			throw new DuplicatedResourceException("book esistente con isbn: " + book.getIsbn());
		}

		// Cerca la categoria 
		Category category = findCategoryInsideBook(book);
		book.setCategory(category);
		
		// Cerca l'editore
		Publisher publisher = findPublisherInsideBook(book);
		book.setPublisher(publisher);

		// Cerca gli autori
		Set<Author> authors = new HashSet<>();

		for (Author author : book.getAuthors()) {
			Author savedAuthor = findAuthorInsideBook(author);
			authors.add(savedAuthor);
		}
		book.setAuthors(authors);

		// Salva il libro
		return bookRepository.save(book);
	}

	@Override
	public Book replaceBookById(Book book, Integer id) { // Book non ha campi nulli, ad eccezione dell'id

		if (book == null) {
			throw new IllegalArgumentException("book nullo");
		}

		if (id == null) {
			throw new IllegalArgumentException("id nullo");
		}

		//Cerca il libro da modificare
		Book replacedBook = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("libro non trovato con id: " + id));

		// Verifica che non esista un altro libro con lo stesso isbn
		if (bookRepository.existsByIsbnAndIdNot(book.getIsbn(), id)) {
			throw new DuplicatedResourceException("ISBN duplicato");
		}

		replacedBook.setTitle(book.getTitle());
		replacedBook.setIsbn(book.getIsbn());
		replacedBook.setLanguage(book.getLanguage());
		replacedBook.setImageUri(book.getImageUri());
		replacedBook.setEdition(book.getEdition());
		replacedBook.setLendable(book.getLendable());

		// Cerca la categoria 
		Category category = findCategoryInsideBook(book);
		replacedBook.setCategory(category);
		
		// Cerca l'editore
		Publisher publisher = findPublisherInsideBook(book);
		replacedBook.setPublisher(publisher);

		// Cerca gli autori
		Set<Author> authors = new HashSet<>();
		for (Author author : book.getAuthors()) {
			Author savedAuthor = findAuthorInsideBook(author);
			authors.add(savedAuthor);
		}
		replacedBook.setAuthors(authors);

		// Salva il book
		return bookRepository.save(replacedBook);
	}

	@Override
	public Book updateBookById(Book book, Integer id) { // Book può avere campi nulli

		if (book == null) {
			throw new IllegalArgumentException("book nullo");
		}

		if (id == null) {
			throw new IllegalArgumentException("id nullo");
		}

		//Cerca il libro da modificare
		Book updatedBook = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("libro non trovato con id: " + id));

		// Verifica che non esista un altro libro con lo stesso isbn
		if (book.getIsbn() != null && !book.getIsbn().isBlank()) {
			if (bookRepository.existsByIsbnAndIdNot(book.getIsbn(), id)) {
				throw new DuplicatedResourceException("ISBN duplicato");
			}
			updatedBook.setIsbn(book.getIsbn());
		}

		// Modifica i campi solo se non sono nulli o vuoti

		if (book.getTitle() != null && !book.getTitle().isBlank()) {
			updatedBook.setTitle(book.getTitle());
		}
		if (book.getLanguage() != null && !book.getLanguage().isBlank()) {
			updatedBook.setLanguage(book.getLanguage());
		}
		if (book.getImageUri() != null && !book.getImageUri().isBlank()) {
			updatedBook.setImageUri(book.getImageUri());
		}
		if (book.getEdition() != null) {
			updatedBook.setEdition(book.getEdition());
		}

		if (book.getLendable() != null) {
			updatedBook.setLendable(book.getLendable());
		}

		if (book.getCategory() != null) {
			// Cerca la categoria 
			Category category = findCategoryInsideBook(book);
			updatedBook.setCategory(category);
		}
		
		if (book.getPublisher() != null) {
		    Publisher publisher = findPublisherInsideBook(book);
		    updatedBook.setPublisher(publisher);
		}

		if (book.getAuthors() != null) {
			// Cerca gli autori
			Set<Author> authors = new HashSet<>();
			for (Author author : book.getAuthors()) {
				Author savedAuthor = findAuthorInsideBook(author);
				authors.add(savedAuthor);
			}
			updatedBook.setAuthors(authors);
		}

		// Salva il book
		return bookRepository.save(updatedBook);

	}

	@Override
	public void deleteBookById(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("id nullo");
		}
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("libro non trovato con id: " + id));

		bookRepository.delete(book);
	}

	@Override
	public List<Book> findBooksByTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("title nullo");
		}
		return bookRepository.findByTitleContainingIgnoreCase(title); // MODIFICATO
	}

	@Override
	public List<Book> findBooksByAuthor(Author author) {
		if (author == null || author.getId() == null) {
			throw new IllegalArgumentException("autore nullo o senza id");
		}
		return bookRepository.findByAuthorsId(author.getId());
	}

	@Override
	public List<Book> findBooksByCategory(Category category) {
		if (category == null || category.getId() == null) {
			throw new IllegalArgumentException("categoria nulla o senza id");
		}
		return bookRepository.findByCategoryId(category.getId());
	}

	private Category findCategoryInsideBook(Book book) {
		return categoryRepository.findById(book.getCategory().getId())
				.orElseThrow(() -> new NotFoundException("categoria non trovata con id: " + book.getCategory().getId()));
	}
	
	private Publisher findPublisherInsideBook(Book book) {
		return publisherRepository.findById(book.getPublisher().getId())
				.orElseThrow(() -> new NotFoundException("editore non trovato con id: " + book.getPublisher().getId()));
	}

	private Author findAuthorInsideBook(Author author) {
		return authorRepository.findById(author.getId())
				.orElseThrow(() -> new NotFoundException("autore non trovato con id: " + author.getId()));
	}

}
