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
        Seat seat = new Seat();
        seat.setSeatNumber(seatRequest.getSeatNumber());
        seat.setSeatType(SeatType.valueOf(seatRequest.getSeatType()));
        seat.setScreen(screen);
        return seatRepository.save(seat);
    }

    public List<Seat> getSeatsByScreenId(Long sceenId){
        return seatRepository.findByScreenScreenId(sceenId);
    }
}
