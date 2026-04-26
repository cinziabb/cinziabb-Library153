package com.generation153.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation153.library.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {}
