package com.generation153.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<List<User>> findByFirstName(String firstName);
	Optional<List<User>> findByLastName(String lastName);
	
	//Rispetta la naming convention di SpringBoot
	Optional<List<User>> findByFirstNameAndLastName(String firstName, String lastName);
	boolean existsByEmail(String email);
	boolean existsByEmailAndIdNot(String email, Integer id);
	
}
