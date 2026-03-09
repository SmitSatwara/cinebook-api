package com.smitsatwara.cinebook.repository;

import com.smitsatwara.cinebook.model.SeatStatus;
import com.smitsatwara.cinebook.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
    List<ShowSeat> findByShowShowId(Long showId);
    List<ShowSeat> findByShowShowIdAndStatus(Long showId, SeatStatus status);
    Optional<ShowSeat> findByShowShowIdAndSeatSeatId(Long showId, Long seatId);
    List<ShowSeat> findByBookingBookingId(Long bookingId);
    List<ShowSeat> findByStatus(SeatStatus seatStatus);
}
