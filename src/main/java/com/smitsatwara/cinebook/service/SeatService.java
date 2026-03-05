package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.SeatRequest;
import com.smitsatwara.cinebook.model.Screen;
import com.smitsatwara.cinebook.model.Seat;
import com.smitsatwara.cinebook.model.SeatType;
import com.smitsatwara.cinebook.repository.ScreenRepository;
import com.smitsatwara.cinebook.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    public Seat addSeat(SeatRequest seatRequest) {

        Screen screen = screenRepository.findById(seatRequest.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found with id: " + seatRequest.getScreenId()));
        if(seatRepository.findBySeatNumberAndScreenScreenId(
                seatRequest.getSeatNumber(),
                seatRequest.getScreenId()).isPresent()) {
            throw new RuntimeException("Seat " + seatRequest.getSeatNumber()
                    + " already exists in this screen");
        }
        Seat seat = new Seat();
        seat.setSeatNumber(seatRequest.getSeatNumber());
        seat.setSeatType(seatRequest.getSeatType());
        seat.setScreen(screen);
        return seatRepository.save(seat);
    }

    public List<Seat> getSeatsByScreenId(Long screenId){
        return seatRepository.findByScreenScreenId(screenId);
    }
}
