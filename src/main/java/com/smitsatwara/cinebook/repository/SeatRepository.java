package com.smitsatwara.cinebook.repository;

import com.smitsatwara.cinebook.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findByScreenScreenId(Long screenId);
}
