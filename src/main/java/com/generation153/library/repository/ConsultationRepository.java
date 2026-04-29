package com.generation153.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Consultation;
import com.generation153.library.entity.Copy;
import com.generation153.library.entity.User;

public interface ConsultationRepository extends JpaRepository<Consultation, Integer>{
	
	List<Consultation> findByUser(User user);
	List<Consultation> findByCopy(Copy copy);
	List<Consultation> findByEndTimeIsNull();
	List<Consultation> findByEndTimeIsNotNull();
}
