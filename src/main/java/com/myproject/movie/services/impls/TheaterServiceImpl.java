package com.myproject.movie.services.impls;

import com.myproject.movie.mappers.TheaterMapper;
import com.myproject.movie.models.entities.Theater;
import com.myproject.movie.repositories.TheaterRepository;
import com.myproject.movie.services.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TheaterServiceImpl implements TheaterService {
    private final TheaterRepository theaterRepository;
    private final TheaterMapper theaterMapper;

    @Override
    public List<Theater> findAll() {
        return theaterRepository.findAll();
    }

    @Override
    public List<Theater> getTheatersByMovieCityBrandAndDate(Integer movieId, Integer cityId, Integer brandId, LocalDate date) {
        List<Theater> theaters = theaterRepository.findByBrandIdAndCityIdAndRoomsScreeningsMovieId(brandId, cityId, movieId);

        return theaters.stream()
                .filter(theater -> theater.getRooms().stream()
                        .flatMap(room -> room.getScreenings().stream())
                        .anyMatch(screening -> screening.getStartTime().toLocalDate().equals(date)))
                .collect(Collectors.toList());
    }

    @Override
    public Theater saveOrUpdate(Integer theater_id, Theater newTheater) {
        Theater existingTheater = findById(theater_id);
        newTheater.setId(theater_id);

        return theaterRepository.save(newTheater);
    }

    @Override
    public void deleteById(Integer theater_id) {
        Theater existingTheater = findById(theater_id);

        theaterRepository.delete(existingTheater);
    }

    @Override
    public Theater findById(Integer theater_id) {
        return theaterRepository.findById(theater_id)
                .orElseThrow(() -> new RuntimeException("Theater not found with id: " + theater_id));
    }
}
