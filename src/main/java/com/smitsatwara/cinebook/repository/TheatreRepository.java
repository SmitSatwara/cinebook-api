package com.smitsatwara.cinebook.repository;

import com.smitsatwara.cinebook.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre,Long> {
    List<Theatre> findByNameAndCity(String name, String city);
    List<Theatre> findByCity(String city);
    Optional<Theatre> findByNameAndCityAndAddress(String name, String city, String address);
}
