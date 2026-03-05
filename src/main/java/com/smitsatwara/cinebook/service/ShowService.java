package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.ShowRequest;
import com.smitsatwara.cinebook.model.*;
import com.smitsatwara.cinebook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final ShowSeatRepository showSeatRepository;
    private final SeatRepository seatRepository;

    //admin can create shows and auto create show seats for the show
    @Transactional
    public Show addShow(ShowRequest showRequest) {

        if(showRepository.findByScreenScreenIdAndShowDateAndShowTime(
                showRequest.getScreenId(),
                showRequest.getShowDate(),
                showRequest.getShowTime()).isPresent()) {
            throw new RuntimeException("Show already exists for the given screen, date and time");
        }

        Movie movie = movieRepository.findById(showRequest.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + showRequest.getMovieId()));
        Screen screen = screenRepository.findById(showRequest.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found with id: " + showRequest.getScreenId()));
        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setShowDate(showRequest.getShowDate());
        show.setShowTime(showRequest.getShowTime());
        show.setPrice(showRequest.getPrice());

        Show savedShow= showRepository.save(show);
        List<Seat> seats = seatRepository.findByScreenScreenId(screen.getScreenId());
        List<ShowSeat> showSeats = seats.stream()
                .map(seat -> {
                    ShowSeat showSeat = new ShowSeat();
                    showSeat.setShow(savedShow);
                    showSeat.setSeat(seat);
                    double multipliedPrice =show.getPrice()
                            * seat.getSeatType().getMultiplier()
                            * show.getMovie().getLanguage().getMultiplier()
                            * getTimingMultiplier(show.getShowTime());
                    showSeat.setPrice(multipliedPrice);
                    showSeat.setStatus(SeatStatus.AVAILABLE);
                    return showSeat;
                }

                )
                .toList();
        showSeatRepository.saveAll(showSeats);
        return savedShow;
    }
    //get all shows for a movie
    public List<Show> getShowsByMovie(Long movieId, LocalDate showDate) {
        return showRepository.findByMovieMovieIdAndShowDate(movieId,showDate);
    }
    //get all shows for a screen
    public List<Show> getShowsByScreen(Long screenId) {
        return showRepository.findByScreenScreenId(screenId);
    }

    public List<ShowSeat> getShowSeats(Long showId) {
        return showSeatRepository.findByShowShowId(showId);
    }

    public List<Show> getShowsByCityAndMovieAndDate(String city, Long movieId, LocalDate showDate) {
        return showRepository.findByScreenTheatreCityAndMovieMovieIdAndShowDate(city, movieId, showDate);
    }


    private double getTimingMultiplier(LocalTime showTime) {
        if (showTime.isBefore(LocalTime.of(12, 0))) return 0.8;  // Morning
        if (showTime.isBefore(LocalTime.of(17, 0))) return 1.0;  // Afternoon
        if (showTime.isBefore(LocalTime.of(21, 0))) return 1.2;  // Evening
        return 1.5;                                               // Night
    }
}
