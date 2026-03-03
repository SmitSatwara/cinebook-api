package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.BookingRequest;
import com.smitsatwara.cinebook.model.*;
import com.smitsatwara.cinebook.repository.BookingRepository;
import com.smitsatwara.cinebook.repository.ShowRepository;
import com.smitsatwara.cinebook.repository.ShowSeatRepository;
import com.smitsatwara.cinebook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    public Booking createBooking(BookingRequest bookingRequest, String email) {
        Show show = showRepository.findById(bookingRequest.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + bookingRequest.getShowId()));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        for(Long seatId : bookingRequest.getSeatIds()){
            ShowSeat showSeat = showSeatRepository.findByShowShowIdAndSeatSeatId(bookingRequest.getShowId(),seatId)
                    .orElseThrow(() -> new RuntimeException("Show seat not found for showId: " + bookingRequest.getShowId() + " and seatId: " + seatId));
            if(showSeat.getStatus()!= SeatStatus.LOCKED) {
                throw new RuntimeException("Seat with id: " + seatId + " is not locked for booking");
            }
             showSeat.setStatus(SeatStatus.BOOKED);
             showSeatRepository.save(showSeat);
        }

        Double totalPrice = show.getPrice() * bookingRequest.getSeatIds().size();
        Booking booking = new Booking();
        booking.setShow(show);
        booking.setUser(user);
        booking.setTotalAmount(totalPrice);
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
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


}
