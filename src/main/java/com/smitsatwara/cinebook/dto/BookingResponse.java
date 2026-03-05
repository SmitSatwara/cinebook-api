package com.smitsatwara.cinebook.dto;

import com.smitsatwara.cinebook.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    //bookingID.movie title ,sowdate, showtime, screen name, seat details(seat number, seat type, price), total price, booking status
    private Long bookingId;
    private String movieTitle;
    private String screenName;
    private LocalDate showDate;
    private LocalTime showTime;
    private String theaterName;
    private BookingStatus bookingStatus;
    private Double totalPrice;
    private List<BookingSeatDetail> seatDetails;
}
