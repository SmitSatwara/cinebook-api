package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.model.SeatStatus;
import com.smitsatwara.cinebook.model.Show;
import com.smitsatwara.cinebook.model.ShowSeat;
import com.smitsatwara.cinebook.repository.SeatRepository;
import com.smitsatwara.cinebook.repository.ShowRepository;
import com.smitsatwara.cinebook.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowSeatService {
    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public List<ShowSeat> getShowSeats(Long showId) {
        return showSeatRepository.findByShowShowId(showId);
    }


    public List<ShowSeat> getAvailableSeats(Long showId) {
        return showSeatRepository.findByShowShowIdAndStatus(showId, SeatStatus.AVAILABLE);
    }

    public ShowSeat lockSeat(Long showId,Long seatId) {
        ShowSeat showSeat = showSeatRepository.findByShowShowIdAndSeatSeatId(showId,seatId)
                .orElseThrow(() -> new RuntimeException("Show seat not found for showId: " + showId + " and seatId: " + seatId));
        if(showSeat.getStatus()!= SeatStatus.AVAILABLE) {
            throw new RuntimeException("Seat is not available for booking");
        }
        showSeat.setStatus(SeatStatus.LOCKED);
        return showSeatRepository.save(showSeat);
    }

    public ShowSeat unlockSeat(Long showId, Long seatId) {
        ShowSeat showSeat = showSeatRepository.findByShowShowIdAndSeatSeatId(showId,seatId)
                .orElseThrow(() -> new RuntimeException("Show seat not found for showId: " + showId + " and seatId: " + seatId));
        if(showSeat.getStatus()!= SeatStatus.LOCKED) {
            throw new RuntimeException("Seat is not locked");
        }
        showSeat.setStatus(SeatStatus.AVAILABLE);
        return showSeatRepository.save(showSeat);
    }


}
