package com.myproject.movie.services.impls;

import com.myproject.movie.models.entities.Brand;
import com.myproject.movie.repositories.BrandRepository;
import com.myproject.movie.services.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        log.info("Fetching all brands from repository");
        List<Brand> brands = brandRepository.findAll();
        log.debug("Retrieved {} brands", brands.size());

        return brands;
    }
}