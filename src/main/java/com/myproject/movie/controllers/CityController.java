package com.myproject.movie.controllers;

import com.myproject.movie.models.entities.City;
import com.myproject.movie.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    @GetMapping
    public List<City> getAllCity() {
        return cityService.getAllCities();
    }
}
