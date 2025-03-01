package com.myproject.movie.services.impls;

import com.myproject.movie.models.entities.City;
import com.myproject.movie.repositories.CityRepository;
import com.myproject.movie.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}
