package com.smitsatwara.cinebook.controller;

import com.smitsatwara.cinebook.model.ShowSeat;
import com.smitsatwara.cinebook.service.ShowSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show-seats")
@RequiredArgsConstructor
public class ShowSeatController {
    private final ShowSeatService showSeatService;

    @GetMapping("/{showId}")
    public ResponseEntity<List<ShowSeat>> getAllSeats(@PathVariable Long showId) {
        return ResponseEntity.ok(showSeatService.getShowSeats(showId));
    }
    @GetMapping("//{showId}/available")
    public ResponseEntity<List<ShowSeat>> getAvailableSeats(@PathVariable Long showId) {
        return ResponseEntity.ok(showSeatService.getAvailableSeats(showId));
    }
    @PutMapping("/{showId}/{seatId}/lock")
    public ResponseEntity<ShowSeat> lockSeat(@PathVariable Long showId,@PathVariable Long seatId) {
        return ResponseEntity.ok(showSeatService.lockSeat(showId,seatId));
    }
    @PutMapping("/{showId}/{seatId}/unlock")
    public ResponseEntity<ShowSeat> unlockSeat(@PathVariable Long showId,@PathVariable Long seatId) {
        return ResponseEntity.ok(showSeatService.unlockSeat(showId,seatId));
    }
}
