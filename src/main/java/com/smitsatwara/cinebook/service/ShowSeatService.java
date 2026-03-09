package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.ShowSeatResponse;
import com.smitsatwara.cinebook.model.SeatStatus;
import com.smitsatwara.cinebook.model.ShowSeat;
import com.smitsatwara.cinebook.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowSeatService {
    private final ShowSeatRepository showSeatRepository;
    private final RedisService redisService;

    public List<ShowSeatResponse> getShowSeats(Long showId) {
        //need to as ShowSeatResponse as we dont want to expose the show and seat details in the response
            return showSeatRepository.findByShowShowId(showId)
                    .stream()
                    .map(showSeat -> {
                        ShowSeatResponse response = new ShowSeatResponse();
                        response.setShowSeatId(showSeat.getShowSeatId());
                        response.setSeatId(showSeat.getSeat().getSeatId());
                        response.setSeatNumber(showSeat.getSeat().getSeatNumber());
                        response.setSeatType(showSeat.getSeat().getSeatType());
                        response.setPrice(showSeat.getPrice());
                        response.setStatus(showSeat.getStatus());
                        return response;
                    }).toList();
    }


    public List<ShowSeatResponse> getAvailableSeats(Long showId) {
        return showSeatRepository.findByShowShowIdAndStatus(showId, SeatStatus.AVAILABLE)
                .stream()
                .map(showSeat -> {
                    ShowSeatResponse response = new ShowSeatResponse();
                    response.setShowSeatId(showSeat.getShowSeatId());
                    response.setSeatId(showSeat.getSeat().getSeatId());
                    response.setSeatNumber(showSeat.getSeat().getSeatNumber());
                    response.setSeatType(showSeat.getSeat().getSeatType());
                    response.setPrice(showSeat.getPrice());
                    response.setStatus(showSeat.getStatus());
                    return response;
                }).toList();
    }

    public ShowSeat lockSeat(Long showId,Long seatId) {
        ShowSeat showSeat = showSeatRepository.findByShowShowIdAndSeatSeatId(showId,seatId)
                .orElseThrow(() -> new RuntimeException("Show seat not found for showId: " + showId + " and seatId: " + seatId));
        if(showSeat.getStatus()!= SeatStatus.AVAILABLE) {
            throw new RuntimeException("Seat is not available for booking");
        }
        showSeat.setStatus(SeatStatus.LOCKED);
        redisService.lockSeat(showId,seatId);
        return showSeatRepository.save(showSeat);
    }

    public ShowSeat unlockSeat(Long showId, Long seatId) {
        ShowSeat showSeat = showSeatRepository.findByShowShowIdAndSeatSeatId(showId,seatId)
                .orElseThrow(() -> new RuntimeException("Show seat not found for showId: " + showId + " and seatId: " + seatId));
        if(showSeat.getStatus()!= SeatStatus.LOCKED) {
            throw new RuntimeException("Seat is not locked");
        }
        showSeat.setStatus(SeatStatus.AVAILABLE);
        redisService.unlockSeat(showId,seatId);
        return showSeatRepository.save(showSeat);
    }


}
