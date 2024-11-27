package com.myproject.movie_booking.repositories;

import com.myproject.movie_booking.models.ScreeningSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningSeatRepository extends JpaRepository<ScreeningSeat, Integer> {
    List<ScreeningSeat> findByScreeningIdInAndStatus(List<Integer> screeningIds, String status);
}
