package com.smitsatwara.cinebook;

import com.smitsatwara.cinebook.dto.BookingRequest;
import com.smitsatwara.cinebook.model.Booking;
import com.smitsatwara.cinebook.model.Show;
import com.smitsatwara.cinebook.model.User;
import com.smitsatwara.cinebook.repository.*;
import com.smitsatwara.cinebook.service.BookingService;
import com.smitsatwara.cinebook.service.RedisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private ShowSeatRepository showSeatRepository;
    @Mock
    private ShowRepository showRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RedisService redisService;

    @InjectMocks
    private BookingService bookingService;
    @Test
    void createBooking_WhenShowNotFound_ShouldThrowException(){
        //Arrange
        ArrayList<Long> seatIds =new ArrayList<>();
        User user = new User();
        user.setEmail("abc@gmial.com");
        seatIds.add(1L);
        BookingRequest bookingRequest = new BookingRequest(1L,seatIds);

        when(showRepository.findById(bookingRequest.getShowId())).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class,()->bookingService.createBooking(bookingRequest,user.getEmail()));
        verify(showRepository,times(1)).findById(1L);
    }
    @Test
    void createBooking_WhenUserNotFound_ShouldThrowException(){
        //Arrange
        ArrayList<Long> seatIds =new ArrayList<>();
        User user = new User();
        user.setEmail("abc@gmail.com");
        Show show = new Show();
        show.setShowId(1L);
        seatIds.add(1L);
        BookingRequest bookingRequest = new BookingRequest(1L,seatIds);

        when(showRepository.findById(bookingRequest.getShowId())).thenReturn(Optional.of(show));
        when(userRepository.findByEmail("abc@gmail.com")).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class,()->bookingService.createBooking(bookingRequest, user.getEmail()));
        verify(userRepository,times(1)).findByEmail("abc@gmail.com");
    }
    @Test
    void createBooking_WhenShowSeatNotFound_ShouldThrowException(){
        //Arrange
        ArrayList<Long> seatIds =new ArrayList<>();
        User user = new User();
        user.setEmail("abc@gmail.com");

        Show show = new Show();
        show.setShowId(1L);
        seatIds.add(1L);

        Booking booking = new Booking();
        booking.setBookingId(1L);
        BookingRequest bookingRequest = new BookingRequest(1L,seatIds);

        when(showRepository.findById(bookingRequest.getShowId())).thenReturn(Optional.of(show));
        when(userRepository.findByEmail("abc@gmail.com")).thenReturn(Optional.of(user));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(showSeatRepository.findByShowShowIdAndSeatSeatId(show.getShowId(),bookingRequest.getSeatIds().get(0))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()->bookingService.createBooking(bookingRequest, user.getEmail()));
        verify(showSeatRepository,times(1)).findByShowShowIdAndSeatSeatId(bookingRequest.getShowId(),bookingRequest.getSeatIds().get(0));

    }
    @Test
    void createBooking_WhenSeatNotLockedInRedis_ShouldThrowException(){}
    @Test
    void createBooking_WhenSeatNotLockedInDB_ShouldThrowException(){}
    @Test
    void createBooking_WhenAllValid_ShouldCreateBookingSuccessfully(){}
}
