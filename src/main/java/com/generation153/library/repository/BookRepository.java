package com.generation153.library.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	
	boolean existsByIsbn(String isbn);
	
	/*
	 * SELECT COUNT(b) > 0
	 * FROM Book b
	 * WHERE b.isbn = :isbn
	 * AND b.id <> :id
	 * 
	 * Controlla se esiste almeno un record tale che ha lo stesso isbn e ha un id diverso
	 * In tal caso non si procede con l'operazione di update
	 */
	List<Book> findByTitleContainingIgnoreCase(String title);
	
	boolean existsByIsbnAndIdNot(String isbn, Integer id);
	
	/*
	 * Trova tutti i Book in cui esiste almeno un Author con questo id
	 * SELECT b
	 * FROM Book b
	 * JOIN b.authors a
	 * WHERE a.id = :authorId
	 */
	List<Book> findByAuthorsId(Integer authorId);
	
	/*
	 * SELECT b
	 * FROM Book b
	 * WHERE b.publisher.id = :publisherId
	 */
	List<Book> findByPublisherId(Integer publisherId);
	
	/*
	 * SELECT b
	 * FROM Book b
	 * WHERE b.category.id = :categoryId
	 */
	List<Book> findByCategoryId(Integer categoryId);
	
}
