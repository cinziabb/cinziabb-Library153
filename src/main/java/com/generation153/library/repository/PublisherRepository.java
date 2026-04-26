package com.generation153.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Publisher;


public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
	
}
