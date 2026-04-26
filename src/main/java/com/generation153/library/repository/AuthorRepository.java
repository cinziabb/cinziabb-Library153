package com.generation153.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
