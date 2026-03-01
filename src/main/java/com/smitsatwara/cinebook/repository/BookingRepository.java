package com.smitsatwara.cinebook.repository;

import com.smitsatwara.cinebook.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByUserUserId(Long userId);
    Optional<Booking> findByBookingIdAndUserUserId(Long bookingId, Long userId);
}
