package com.smitsatwara.cinebook.controller;

import com.smitsatwara.cinebook.dto.MovieRequest;
import com.smitsatwara.cinebook.model.Movie;
import com.smitsatwara.cinebook.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok( movieService.getMovieById(id));
    }
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody @Valid MovieRequest movieRequest) {
        return ResponseEntity.ok(movieService.addMovie(movieRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

}
