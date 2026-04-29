package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.Consultation;
import com.generation153.library.entity.Copy;
import com.generation153.library.entity.User;

//vedere quali libri sono attualmente disponibili
//vedere quali libri sono più richiesti o più prestati
//visualizzare i libri attualmente in prestito
//visualizzare i prestiti effettuati da uno specifico utente


public interface ConsultationService {
	
	List<Consultation> findAllConsultations();
	Consultation saveConsultation(Consultation consultation);
	Consultation findConsultationById(Integer id);
	Consultation updateConsunltationById(Consultation consultation, Integer id);
	void deleteConsutationById(Integer id);
	
	Consultation startConsultation(Copy copy, User user);
	void endConsultation(Consultation consultation, Integer id);
	
	List<Consultation> findConsultationByUser(User user);
	List<Consultation> findConsultationByCopy(Copy copy);
	List<Consultation> findActiveConsultation();
	List<Consultation> findCompletedConsultation();
	
	List<Object[]> findMostWantedBook();
	/*
	 * SELECT DISTINCT b.title
	 * FROM consultation co
	 * JOIN copy c ON co.copy_id = c.id
	 * JOIN book b ON c.book_id = b.id
	 * WHERE c.available = false
	 * ORDER BY b.title;
	 * 
	 * SELECT DISTINCT c.copy.book.title
	 * FROM Consultation c
	 * WHERE c.copy.available
	 * ORDER BY c.copy.book.title
	 */
	
}
