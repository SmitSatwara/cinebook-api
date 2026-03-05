package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.BookingRequest;
import com.smitsatwara.cinebook.model.*;
import com.smitsatwara.cinebook.repository.BookingRepository;
import com.smitsatwara.cinebook.repository.ShowRepository;
import com.smitsatwara.cinebook.repository.ShowSeatRepository;
import com.smitsatwara.cinebook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    @Transactional
    public Booking createBooking(BookingRequest bookingRequest, String email) {
        Show show = showRepository.findById(bookingRequest.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + bookingRequest.getShowId()));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        Booking booking = new Booking();
        booking.setShow(show);
        booking.setUser(user);
        booking.setTotalAmount(0.0);
        booking.setStatus(BookingStatus.CONFIRMED);
        Booking savedBooking =  bookingRepository.save(booking); // bookingId is generated after saving, so we need to save the booking first before updating show seats with booking reference

        //now link the booked show seats to the booking
        double totalPrice = 0.0;
        for(Long seatId : bookingRequest.getSeatIds()){
            ShowSeat showSeat = showSeatRepository.findByShowShowIdAndSeatSeatId(bookingRequest.getShowId(),seatId)
                    .orElseThrow(() -> new RuntimeException("Show seat not found for showId: " + bookingRequest.getShowId() + " and seatId: " + seatId));
            if(showSeat.getStatus()!= SeatStatus.LOCKED) {
                throw new RuntimeException("Seat with id: " + seatId + " is not locked for booking");
            }
            showSeat.setStatus(SeatStatus.BOOKED);
            showSeat.setBooking(savedBooking);
            totalPrice += showSeat.getPrice();
            showSeatRepository.save(showSeat);
        }
        savedBooking.setTotalAmount(totalPrice);
         return bookingRepository.save(savedBooking);
    }

    public Booking getBookingById(Long bookingId){
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
    }


    public List<Booking> getBookingByUser(String email){
        User user  =  userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return bookingRepository.findByUserUserId(user.getUserId());
    }
    @Transactional
    public Booking cancelBooking(Long bookingId, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        Booking booking = bookingRepository.findByBookingIdAndUserUserId(bookingId, user.getUserId())
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId + " for user with email: " + email));

        LocalDateTime showTime = LocalDateTime.of(booking.getShow().getShowDate(), booking.getShow().getShowTime());

        if(LocalDateTime.now().isAfter(showTime.minusHours(24))) {
            throw new RuntimeException("Bookings can only be cancelled at least 24 hours before the show time");
        }

        if(booking.getStatus()== BookingStatus.CANCELLED || booking.getStatus()== BookingStatus.PENDING) {
            throw new RuntimeException("Only confirmed bookings can be cancelled");
        }

        showSeatRepository.findByBookingBookingId(bookingId)
                .forEach(showSeat -> {
                    showSeat.setStatus(SeatStatus.AVAILABLE);
                    showSeat.setBooking(null);
                    showSeatRepository.save(showSeat);
                });
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }
}
