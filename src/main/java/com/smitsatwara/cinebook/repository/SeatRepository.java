package com.smitsatwara.cinebook.repository;

import com.smitsatwara.cinebook.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findByScreenScreenId(Long screenId);
    Optional<Seat> findBySeatNumberAndScreenScreenId(String seatNumber, Long screenId);
}
