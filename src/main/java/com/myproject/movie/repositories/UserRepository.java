package com.myproject.movie.repositories;

import com.myproject.movie.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByIsActiveTrue();
    Optional<User> findByEmail(String email);
}