package com.smitsatwara.cinebook.repository;

import com.smitsatwara.cinebook.model.Language;
import com.smitsatwara.cinebook.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    Optional<Movie> findByTitle(String title);
    Optional<Movie> findByTitleAndLanguage(String title, Language language);
}
