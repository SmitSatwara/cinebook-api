package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.ShowRequest;
import com.smitsatwara.cinebook.model.Movie;
import com.smitsatwara.cinebook.model.Screen;
import com.smitsatwara.cinebook.model.Show;
import com.smitsatwara.cinebook.repository.MovieRepository;
import com.smitsatwara.cinebook.repository.ScreenRepository;
import com.smitsatwara.cinebook.repository.ShowRepository;
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

    //admin can create shows
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
        return showRepository.save(show);

    }
    //get all shows for a movie
    public List<Show> getShowByMovie(Long movieId, LocalDate showDate) {
        return showRepository.findByMovieMovieIdAndShowDate(movieId,showDate);
    }
    //get all shows for a screen
    public List<Show> getShowByScreen(Long screenId) {
        return showRepository.findByScreenScreenId(screenId);
    }
}
