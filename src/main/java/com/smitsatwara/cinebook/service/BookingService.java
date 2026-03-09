package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.BookingRequest;
import com.smitsatwara.cinebook.dto.BookingResponse;
import com.smitsatwara.cinebook.dto.BookingSeatDetail;
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
    private final RedisService redisService;

    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest, String email) {
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
            if(!redisService.isSeatLocked(bookingRequest.getShowId(),seatId)){
                throw new RuntimeException("Seat with id: " + seatId + " is not locked for booking" + " or lock has expired. Please select the seat again to lock it before booking");
            }
            if(showSeat.getStatus()!= SeatStatus.LOCKED) {
                throw new RuntimeException("Seat with id: " + seatId + " is not locked for booking");
            }
            showSeat.setStatus(SeatStatus.BOOKED);
            showSeat.setBooking(savedBooking);
            totalPrice += showSeat.getPrice();
            redisService.unlockSeat(bookingRequest.getShowId(),seatId); // unlock the seat in redis after booking is confirmed
            showSeatRepository.save(showSeat);
        }
        savedBooking.setTotalAmount(totalPrice);
        bookingRepository.save(savedBooking);
        return toBookingResponse(savedBooking);
    }

    public BookingResponse getBookingById(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        return toBookingResponse(booking);
    }


    public List<BookingResponse> getBookingByUser(String email){
        User user  =  userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return bookingRepository.findByUserUserId(user.getUserId())
                .stream()
                .map(this::toBookingResponse)
                .toList();
    }
    @Transactional
    public BookingResponse cancelBooking(Long bookingId, String email){

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

        BookingResponse response = toBookingResponse(booking);

        showSeatRepository.findByBookingBookingId(bookingId)
                .forEach(showSeat -> {
                    showSeat.setStatus(SeatStatus.AVAILABLE);
                    showSeat.setBooking(null);
                    showSeatRepository.save(showSeat);
                });
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        return response;
    }

    private BookingResponse toBookingResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getBookingId());
        response.setMovieTitle(booking.getShow().getMovie().getTitle());
        response.setScreenName(booking.getShow().getScreen().getName());
        response.setShowDate(booking.getShow().getShowDate());
        response.setShowTime(booking.getShow().getShowTime());
        response.setTheaterName(booking.getShow().getScreen().getTheatre().getName());
        response.setBookingStatus(booking.getStatus());
        response.setTotalPrice(booking.getTotalAmount());
        List<BookingSeatDetail> seatDetails = showSeatRepository.findByBookingBookingId(booking.getBookingId())
                .stream()
                .map(showSeat -> {
                    BookingSeatDetail seatDetail = new BookingSeatDetail();
                    seatDetail.setSeatNumber(showSeat.getSeat().getSeatNumber());
                    seatDetail.setSeatType(showSeat.getSeat().getSeatType());
                    seatDetail.setPrice(showSeat.getPrice());
                    return seatDetail;
                }).toList();
        response.setSeatDetails(seatDetails);
        return response;
    }


}
