package com.myproject.movie.services.impls;

import com.myproject.movie.models.Theater;
import com.myproject.movie.repositories.TheaterRepository;
import com.myproject.movie.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterServiceImpl implements TheaterService {
    @Autowired
    private TheaterRepository theaterRepository;

    @Override
    public List<Theater> findAll() {
        return theaterRepository.findAll();
    }

    @Override
    public Theater findById(Integer id) {
        return theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater not found with id: " + id));
    }

    @Override
    public Theater saveOrUpdate(Integer id, Theater newTheater) {
        Theater existingTheater = findById(id);
        newTheater.setId(id);

        return theaterRepository.save(newTheater);
    }

    @Override
    public Theater updatePartialTheaterById(Integer id, Theater updatedData) {
        Theater existingTheater = findById(id);

        if (updatedData.getName() != null) {
            existingTheater.setName(updatedData.getName());
        }
        if (updatedData.getAddress() != null) {
            existingTheater.setAddress(updatedData.getAddress());
        }
        if (updatedData.getPhone() != null) {
            existingTheater.setPhone(updatedData.getPhone());
        }
        if (updatedData.getDescription() != null) {
            existingTheater.setDescription(updatedData.getDescription());
        }
        if (updatedData.getCity() != null) {
            existingTheater.setCity(updatedData.getCity());
        }
        if (updatedData.getBrand() != null) {
            existingTheater.setBrand(updatedData.getBrand());
        }
        if (updatedData.getIsActive() != null) {
            existingTheater.setIsActive(updatedData.getIsActive());
        }

        return theaterRepository.save(existingTheater);
    }

    @Override
    public void deleteById(Integer id) {
        Theater existingTheater = findById(id);
        theaterRepository.delete(existingTheater);
    }
}
