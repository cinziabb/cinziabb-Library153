package com.generation153.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

	
	List<Publisher>findByName(String name);
//<<<<<<< HEAD
	
	List<Publisher>findByNameStartingWith(String name);

}
//=======
//	List<Publisher>findByNameStartingWith(String name);
	
//}
//>>>>>>> refs/remotes/origin/develop
