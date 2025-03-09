package com.myproject.movie.services.impls;

import com.myproject.movie.models.entities.City;
import com.myproject.movie.repositories.CityRepository;
import com.myproject.movie.services.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        log.info("Fetching all cities from repository");
        List<City> cities = cityRepository.findAll();
        log.debug("Retrieved {} cities", cities.size());

        return cities;
    }
}
