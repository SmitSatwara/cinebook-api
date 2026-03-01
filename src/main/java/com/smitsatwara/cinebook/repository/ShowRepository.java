package com.smitsatwara.cinebook.repository;

import com.smitsatwara.cinebook.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show,Long> {
    List<Show> findByScreenScreenId(Long screenId);
    List<Show> findByMovieMovieIdAndShowDate(Long movieId, LocalDate showDate);}
