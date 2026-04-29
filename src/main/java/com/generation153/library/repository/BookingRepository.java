package com.generation153.library.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Booking;
import com.generation153.library.entity.EnumBookingStatus;
import com.generation153.library.entity.User;
import com.generation153.library.entity.Book;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	
	List<Booking> findByUser(User user);
	List<Booking> findByBook(Book book);
	List<Booking> findByStatus(EnumBookingStatus status);
}
	