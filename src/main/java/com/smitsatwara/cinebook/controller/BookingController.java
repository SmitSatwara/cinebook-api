package com.smitsatwara.cinebook.controller;

import com.smitsatwara.cinebook.dto.BookingRequest;
import com.smitsatwara.cinebook.model.Booking;
import com.smitsatwara.cinebook.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> bookSeats(@RequestBody @Valid BookingRequest bookingRequest) {
        String userEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return ResponseEntity.ok(bookingService.createBooking(bookingRequest, userEmail));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<Booking>> getMyBookings() {
        String userEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return ResponseEntity.ok(bookingService.getBookingByUser(userEmail));
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId) {
        String userEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId, userEmail));
    }
}