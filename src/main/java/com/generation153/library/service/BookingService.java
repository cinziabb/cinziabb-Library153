package com.generation153.library.service;

import java.util.List;

import com.generation153.library.entity.Booking;
import com.generation153.library.entity.User;


public interface BookingService {
	
	//crea una prenotazione
	Booking saveBooking(Booking booking);
	//trova tutte le prenotazioni
	List<Booking> findAllBookings();
	//trova prenotazione in base all'Id
	Booking findBookingById(Integer id);
	//aggiorna una prenotazione in base all'id, modificando tutti i dati
	Booking replaceBookingbyId(Booking booking, Integer Id);  //PUT
	//aggiorna una prenotazione in base all'id, modificando solo i dati di interesse
	Booking updateBookingbyId(Booking booking, Integer id);		//PATCH
	
	
	//restituisce le prenotazioni di un utente
	List<Booking> findBookingsByUser(User id);
	
	//restituisce le prenotazioni in base allo stato
	List<Booking> finBookingsByStatus (Enum Bostatus);
	//cancella una prenotazione in base all'id'
	void deleteBookingbyId(Integer id); //eliminare una prenotzione
}
