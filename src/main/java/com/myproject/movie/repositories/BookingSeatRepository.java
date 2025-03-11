package com.myproject.movie.repositories;

import com.myproject.movie.models.entities.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingSeatRepository  extends JpaRepository<BookingSeat, Long> {
    List<BookingSeat> findByBookingId(Long bookingId);
}
