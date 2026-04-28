package com.generation153.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.generation153.library.entity.Book;
import com.generation153.library.entity.Booking;
import com.generation153.library.entity.EnumBookingStatus;
import com.generation153.library.entity.User;
import com.generation153.library.exception.DuplicatedResourceException;
import com.generation153.library.exception.NotFoundException;
import com.generation153.library.repository.BookRepository;
import com.generation153.library.repository.BookingRepository;
import com.generation153.library.repository.UserRepository;

@Service
public class BookingServiceImpl implements BookingService {
	
	//Dependency Injection
	private final BookingRepository bookingrepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;
	
	public BookingServiceImpl(BookingRepository bookingrepository, UserRepository userRepository, BookRepository bookRepository) {
		this.bookingrepository = bookingrepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}

	@Override
	public Booking saveBooking(Booking booking) {
		
		if (booking == null) {
			throw new IllegalArgumentException("Prenotazione nulla");
		}

		if (bookingrepository.existsById(booking.getId())) {
			throw new DuplicatedResourceException("Esistente con id: " + booking.getId());
		}
		
		//cerca l'utente che fa la prenotazione e associalo
		User user = findUserInsideBooking(booking);
		booking.setUser(user);
		
		//cerca il libro relativo alla prenotazione e associalo
		Book book = findBookInsideBooking(booking);
		booking.setBook(book);
		 
		//salva la prenotazione
		bookingrepository.save(booking);

		return booking;
	}
	
	@Override
	public List<Booking> findAllBookings() {
		return bookingrepository.findAll();
	}

	@Override
	public Booking findBookingById(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Id nullo");
		}
		Optional<Booking> optBooking = bookingrepository.findById(id);
		return optBooking
				.orElseThrow(() -> new NotFoundException("Prenotazione non trovata con id: " + id));
	}

	@Override
	public Booking replaceBookingbyId(Booking booking, Integer Id) {
		
		//la prenotazione non ha campi nulli, tranne l'id suo, dell'utente e del libro
		
		//controlla che la prenotazione non sia nulla
		if (booking == null) {
			throw new IllegalArgumentException("Prenotazione nulla!");
		}
		
		//controlla che la l'id passato come parametro non sia nullo
		if (Id == null) {
			throw new IllegalArgumentException("Id nullo!");
		}
		
		//cerca la prenotazione da modificare
		Booking replacedBooking = bookingrepository.findById(Id)
				.orElseThrow(() -> new NotFoundException("Prenotazione non trovata con id: " + Id));
				
		//aggiorna tutti i dati della prenotazione (l'id non va aggiornato perchè è già presente)
		replacedBooking.setDate(booking.getDate());
		replacedBooking.setStatus(booking.getStatus());
		
		//cerca e associa l'utente
		User user = findUserInsideBooking(booking);
		replacedBooking.setUser(user);
		
		//cerca e associa il libro
		Book book = findBookInsideBooking(booking);
		replacedBooking.setBook(book);
		
		//salva la prenotazione con i dati aggiornati
		return bookingrepository.save(replacedBooking);
	}

	@Override
	public Booking updateBookingbyId(Booking booking, Integer id) {
		
		// la prenotazione può avere campi nulli
		
		//controlla che la prenotazione non sia nulla
		if (booking == null) {
			throw new IllegalArgumentException("Prenotazione nulla!");
		}
		
		//controlla che la l'id passato come parametro non sia nullo
		if (id == null) {
			throw new IllegalArgumentException("Id nullo!");
		}
		
		//cerca la prenotazione da modificare
		Booking updatedBooking = bookingrepository.findById(id)
						  .orElseThrow(() -> new NotFoundException("prenotazione non trovata con id: " + id));
		
		// Modifica i campi solo se non sono nulli
		
		if (booking.getDate() != null) {
			updatedBooking.setDate(booking.getDate());
		}
		
		if (booking.getStatus() != null) {
			updatedBooking.setStatus(booking.getStatus());
		}
		
		if (booking.getUser() != null) {
			//cerca e associa l'utente
			User user = findUserInsideBooking(booking);
			updatedBooking.setUser(user);
		}
		
		if (booking.getBook() != null) {
			//cerca e associa il libro
			Book book = findBookInsideBooking(booking);
			updatedBooking.setBook(book);
		}
		
		//salva la prenotazione con i dati aggiornati
		return bookingrepository.save(updatedBooking);
	}

	@Override
	public List<Booking> findBookingsByUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("L'utente è nullo");
		}
		List<Booking>bookings = bookingrepository.findByUser(user); 
		if (bookings.isEmpty()) {
			throw new NotFoundException("Prenotazione non trovata per questo utente");
		}
		return bookings;
	}
	
	@Override
	public List<Booking> findBookingsByBook(Book book) {
			if (book == null) {
				throw new IllegalArgumentException("Questo libro è nullo");
			}
			List<Booking>bookings = bookingrepository.findByBook(book); 
			if (bookings.isEmpty()) {
				throw new NotFoundException("Prenotazione non trovata per questo libro");
			}
			return bookings;
	}

	@Override
	public List<Booking> finBookingsByStatus(EnumBookingStatus status) {
		if (status == null) {
			throw new IllegalArgumentException("Stato nullo");
		}
		List<Booking>bookings = bookingrepository.findByStatus(status); 
		if (bookings.isEmpty()) {
			throw new NotFoundException("Nessuna prenotazione trovata con stato " + status);
		}
		return bookings;
	}

	@Override
	public void deleteBookingbyId(Integer id) {
		if(id == null){
			throw new IllegalArgumentException("Id nullo");
		}
		bookingrepository.deleteById(id);
	}
		
	//METODI PRIVATI
	
	/*
	*	trova l'utente in base all'id dell'utente contenuto nella prenotazione
	*/
	private User findUserInsideBooking(Booking booking) {
		return userRepository.findById(booking.getUser().getId())
				.orElseThrow(() -> new NotFoundException("utente non trovato con id: " + booking.getUser().getId()));
	}
	
	/*
	*	trova il libro in base all'id del libro contenuto nella prenotazione
	*/
	private Book findBookInsideBooking(Booking booking) {
		return bookRepository.findById(booking.getBook().getId())
				.orElseThrow(() -> new NotFoundException("libro non trovato con id: " + booking.getBook().getId()));
		
	}
	
}
