package com.smitsatwara.cinebook.controller;

import com.smitsatwara.cinebook.dto.SeatRequest;
import com.smitsatwara.cinebook.model.Seat;
import com.smitsatwara.cinebook.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<Seat> addSeat(@RequestBody @Valid SeatRequest seatRequest){
        return ResponseEntity.ok(seatService.addSeat(seatRequest));
    }
    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<Seat>> getSeatsByScreenId(@PathVariable Long screenId){
        return ResponseEntity.ok(seatService.getSeatsByScreenId(screenId));
    }

}