package com.myproject.movie.repositories;

import com.myproject.movie.models.entities.ScreeningSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScreeningSeatRepository extends JpaRepository<ScreeningSeat, Long> {
    List<ScreeningSeat> findByScreeningId(Long screeningId);
    List<ScreeningSeat> findByIdIn(List<Long> ids);
}
