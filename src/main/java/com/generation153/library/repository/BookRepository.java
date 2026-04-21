package com.generation153.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	
	boolean existsByIsbn(String isbn);

}
