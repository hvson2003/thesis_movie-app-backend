package com.myproject.movie.services.impls;

import com.myproject.movie.models.entities.Brand;
import com.myproject.movie.repositories.BrandRepository;
import com.myproject.movie.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
