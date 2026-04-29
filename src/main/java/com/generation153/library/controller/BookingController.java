package com.generation153.library.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation153.library.entity.Book;
import com.generation153.library.entity.Booking;
import com.generation153.library.entity.User;
import com.generation153.library.service.BookingService;


@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

		//Dependency Injection
		private BookingService bookingService;

		public BookingController(BookingService bookingService) {
			this.bookingService = bookingService;
		}
		
		// API: GET + http://localhost:8080/api/v1/bookings
		@GetMapping()
		public List<Booking> getAllBookings() {
			return bookingService.findAllBookings();
		}
		
		// API: GET + http://localhost:8080/api/v1/bookings/1 
		@GetMapping("/{id}")
		public Booking getBookingById(@PathVariable Integer id) {
			return bookingService.findBookingById(id);
		}
		
		// API: GET + http://localhost:8080/api/v1/bookings/user/1
				@GetMapping("/user/{userId}")
				public List<Booking>getBookingsByUser(@PathVariable User user) {
					return bookingService.findBookingsByUser(user);
				}
		// API: GET + http://localhost:8080/api/v1/bookings/book/1
				@GetMapping("/book/{bookId}")
				public List<Booking>getBookingsByBook(@PathVariable Book book) {
					return bookingService.findBookingsByBook(book);
				}
		
		// PUT
		@PutMapping("/{id}")
		public Booking replaceBookingById(@RequestBody Booking booking, @PathVariable Integer id) {
			return bookingService.replaceBookingbyId(booking, id);
		}
		
		//PATCH
		@PatchMapping("/{id}")
		public Booking updateBookingById(@RequestBody Booking booking, @PathVariable Integer id) {
			return bookingService.updateBookingbyId(booking, id);
		}
		// API: POST + http://localhost:8080/api/v1/bookings
		@PostMapping()
		public Booking saveBooking(@RequestBody Booking booking) {
			return bookingService.saveBooking(booking);
		}
		
		//La risposta HTTP sarà 204 no content
		@DeleteMapping("/{id}")
		public void deleteBooking(@PathVariable Integer id) {
			 bookingService.deleteBookingbyId(id);
		}
		
}
