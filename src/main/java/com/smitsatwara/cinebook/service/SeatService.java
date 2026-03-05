package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.SeatRequest;
import com.smitsatwara.cinebook.dto.SeatResponse;
import com.smitsatwara.cinebook.model.Screen;
import com.smitsatwara.cinebook.model.Seat;
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

    public SeatResponse addSeat(SeatRequest seatRequest) {

        Screen screen = screenRepository.findById(seatRequest.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found with id: " + seatRequest.getScreenId()));
        List<Seat> existingSeats = seatRepository.findByScreenScreenId(seatRequest.getScreenId());

        // capacity check
        if (existingSeats.size() >= screen.getTotalSeats()) {
            throw new RuntimeException("Screen " + screen.getName()
                    + " is already at full capacity of " + screen.getTotalSeats() + " seats");
        }

        // duplicate check
        boolean seatExists = existingSeats.stream()
                .anyMatch(s -> s.getSeatNumber().equals(seatRequest.getSeatNumber()));
        if (seatExists) {
            throw new RuntimeException("Seat " + seatRequest.getSeatNumber()
                    + " already exists in this screen");
        }
        Seat seat = new Seat();
        seat.setSeatNumber(seatRequest.getSeatNumber());
        seat.setSeatType(seatRequest.getSeatType());
        seat.setScreen(screen);
        seatRepository.save(seat);
        return toSeatResponse(seat);
    }

    public List<SeatResponse> getSeatsByScreenId(Long screenId) {
        return seatRepository.findByScreenScreenId(screenId)
                .stream()
                .map(this::toSeatResponse)
                .toList();
    }

    private SeatResponse toSeatResponse(Seat seat) {
        SeatResponse response = new SeatResponse();
        response.setSeatId(seat.getSeatId());
        response.setSeatNumber(seat.getSeatNumber());
        response.setSeatType(seat.getSeatType());
        response.setScreenName(seat.getScreen().getName());
        response.setTheaterName(seat.getScreen().getTheatre().getName());
        response.setCity(seat.getScreen().getTheatre().getCity());
        return response;
    }
}
