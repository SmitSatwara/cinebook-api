package com.smitsatwara.cinebook.repository;

import com.smitsatwara.cinebook.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre,Long> {
    Optional<Theatre> findByNameAndCity(String name, String city);
}
