package com.generation153.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.generation153.library.entity.Author;
import com.generation153.library.entity.Book;
import com.generation153.library.entity.Category;
import com.generation153.library.entity.Publisher;

public interface BookRepository extends JpaRepository<Book, Integer> {
	
	boolean existsByIsbn(String isbn);
	
	Optional<Book> findByIsbn(String isbn);
	
	/*
	 * SELECT b 
	 * FROM Book b
	 * WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))
	 */
	List<Book> findByTitleContainingIgnoreCase(String title);
	
	/*
	 * SELECT COUNT(b) > 0
	 * FROM Book b
	 * WHERE b.isbn = :isbn
	 * AND b.id <> :id
	 * 
	 * Controlla se esiste almeno un record tale che ha lo stesso isbn e ha un id diverso
	 * In tal caso non si procede con l'operazione di update
	 */
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
	
	@Query("""
		    SELECT DISTINCT b
		    FROM Book b
		    JOIN b.authors a
		    WHERE LOWER(a.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))
		      AND LOWER(a.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))
		""")
		List<Book> findBooksByAuthorName(
		        @Param("firstName") String firstName,
		        @Param("lastName") String lastName
		);
	
	/*
	 * SELECT b
	 * FROM Book b
	 * WHERE LOWER(b.category.name) LIKE LOWER('%nome%')
	 */	
	List<Book> findByCategoryNameContainingIgnoreCase(String categoryName);

	/*
	 * SELECT b
	 * FROM Book b
	 * WHERE LOWER(b.publisher.name) LIKE LOWER('%nome%')
	 */
	List<Book> findByPublisherNameContainingIgnoreCase(String publisherName);
}
