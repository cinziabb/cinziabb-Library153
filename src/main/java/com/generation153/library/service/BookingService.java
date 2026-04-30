package com.generation153.library.service;

import java.util.List;


import com.generation153.library.entity.Booking;
import com.generation153.library.entity.EnumBookingStatus;

public interface BookingService {
	
	/**
	*	crea una prenotazione
	*	@param booking la prenotazione da creare
	*	@return la prenotazione creata
	*	@throws 
	*/
	Booking saveBooking(Booking booking);

	/**
	*	trova tutte le prenotazioni
	*	@return la lista contenente tutte le prenotazioni
	*/
	List<Booking> findAllBookings();
	
	/**
	*	trova prenotazione in base all'Id
	*	@param id l'id della prenotazione da trovare
	*	@return la prenotazione con l'id specificato, se esiste
	*	@throws 
	*/
	Booking findBookingById(Integer id);
	
	/**
	*	aggiorna una prenotazione in base all'id, modificando tutti i dati
	*	@param booking
	*	@param Id
	*	@return 
	*	@throws 
	*/
	Booking replaceBookingbyId(Booking booking, Integer Id);  //PUT
	
	/**
	*	aggiorna una prenotazione in base all'id, modificando solo i dati di interesse
	*	@param booking
	*	@param id
	*	@return 
	*	@throws 
	*/
	Booking updateBookingbyId(Booking booking, Integer id);		//PATCH
	
	/**
	*	restituisce le prenotazioni di un utente in base all'id dell'utente
	*	@param id
	*	@return 
	*	@throws 
	*/
	List<Booking> findBookingsByUser(Integer id);
	
	/**
	*	restituisce le prenotazioni relative ad un libro in base all'id del libro
	*	@param id
	*	@return 
	*	@throws 
	*/
	List<Booking> findBookingsByBook(Integer id);
	
	/**
	*	restituisce le prenotazioni in base allo stato
	*	@param status
	*	@return 
	*	@throws 
	*/
	List<Booking> finBookingsByStatus(EnumBookingStatus status);
	
	/**
	*	cancella una prenotazione in base all'id
	*	@param id
	*	@return 
	*	@throws 
	*/
	boolean deleteBookingbyId(Integer id);
}
