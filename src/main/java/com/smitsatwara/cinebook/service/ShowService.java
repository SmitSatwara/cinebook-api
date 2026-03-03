package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.ShowRequest;
import com.smitsatwara.cinebook.model.*;
import com.smitsatwara.cinebook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Show addShow(ShowRequest showRequest) {
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
        //create show seats for the show
        List<Seat> seats = seatRepository.findByScreenScreenId(screen.getScreenId());
        for (Seat seat : seats) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(savedShow);
            showSeat.setSeat(seat);
            showSeat.setStatus(SeatStatus.AVAILABLE);
            showSeatRepository.save(showSeat);
        }
        return savedShow;

    }
    //get all shows for a movie
    public List<Show> getShowByMovie(Long movieId, LocalDate showDate) {
        return showRepository.findByMovieMovieIdAndShowDate(movieId,showDate);
    }
    //get all shows for a screen
    public List<Show> getShowByScreen(Long screenId) {
        return showRepository.findByScreenScreenId(screenId);
    }

    public List<ShowSeat> getShowSeats(Long showId) {
        return showSeatRepository.findByShowShowId(showId);
    }
}
