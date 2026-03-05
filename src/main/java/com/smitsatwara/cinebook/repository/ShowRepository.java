package com.smitsatwara.cinebook.repository;

import com.smitsatwara.cinebook.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show,Long> {
    List<Show> findByScreenScreenId(Long screenId);
    List<Show> findByMovieMovieIdAndShowDate(Long movieId, LocalDate showDate);
    List<Show> findByScreenTheatreCityAndMovieMovieIdAndShowDate(String city, Long movieId, LocalDate showDate);
    Optional<Show> findByScreenScreenIdAndShowDateAndShowTime(Long screenId, LocalDate showDate, LocalTime showTime);
}