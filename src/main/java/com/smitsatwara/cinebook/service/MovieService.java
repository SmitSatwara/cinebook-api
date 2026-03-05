package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.MovieRequest;
import com.smitsatwara.cinebook.model.Movie;
import com.smitsatwara.cinebook.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    //admin can add movies
    public Movie addMovie(MovieRequest movieRequest) {
        if(movieRepository.findByTitleAndLanguage(movieRequest.getTitle(), movieRequest.getLanguage()).isPresent()) {
            throw new RuntimeException("Movie already exists with the same title and language");
        }
        Movie movie = new Movie();
        movie.setTitle(movieRequest.getTitle());
        movie.setGenre(movieRequest.getGenre());
        movie.setLanguage(movieRequest.getLanguage());
        movie.setDuration(movieRequest.getDuration());
        movie.setRating(movieRequest.getRating());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie.setDescription(movieRequest.getDescription());
        return movieRepository.save(movie);
    }
    //get all movies
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long movieId) {
        return  movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));
    }

    public void deleteMovie(Long movieId) {
        if(!movieRepository.existsById(movieId)){
            throw new RuntimeException("Movie not found with id: " + movieId);
        }
        movieRepository.deleteById(movieId);
    }

}
