package com.myproject.movie.controllers;

import com.myproject.movie.models.entities.Brand;
import com.myproject.movie.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public List<Brand> getAllMovies() {
        return brandService.getAllBrands();
    }
}
