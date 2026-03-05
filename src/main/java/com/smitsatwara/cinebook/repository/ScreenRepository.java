package com.smitsatwara.cinebook.repository;

import com.smitsatwara.cinebook.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenRepository extends JpaRepository<Screen,Long> {
    List<Screen> findByTheatreTheatreId(Long theatreId);
    Optional<Screen> findByNameAndTheatreTheatreId(String name, Long theatreId);
}
