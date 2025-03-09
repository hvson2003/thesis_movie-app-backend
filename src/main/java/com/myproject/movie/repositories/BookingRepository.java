package com.myproject.movie.repositories;

import com.myproject.movie.models.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
