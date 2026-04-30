package com.generation153.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.generation153.library.entity.Consultation;
import com.generation153.library.entity.Copy;
import com.generation153.library.entity.User;

public interface ConsultationRepository extends JpaRepository<Consultation, Integer>{
	
	List<Consultation> findByUser(User user);
	List<Consultation> findByCopy(Copy copy);
	List<Consultation> findByEndTimeIsNull();
	List<Consultation> findByEndTimeIsNotNull();
	@Query ("""
			SELECT c.copy.book.title, COUNT(c) 
			FROM Consultation c 
			GROUP BY c.copy.book.title 
			ORDER BY COUNT(c) DESC
			""")
	List<Object[]> findMostConsulted();
	@Query("""
			SELECT DISTINCT c.copy.book.title 
			FROM Consultation c 
			WHERE c.copy.available 
			ORDER BY c.copy.book.title
			""")
	List<Object> findBorrowedBook();
}
