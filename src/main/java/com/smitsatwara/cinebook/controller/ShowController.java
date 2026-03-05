package com.smitsatwara.cinebook.controller;

import com.smitsatwara.cinebook.dto.ShowRequest;
import com.smitsatwara.cinebook.dto.ShowResponse;

import com.smitsatwara.cinebook.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shows")
public class ShowController {
    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ShowResponse> addShow(@RequestBody @Valid ShowRequest showRequest) {
        return ResponseEntity.ok(showService.addShow(showRequest));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowResponse>> getShowsByMovie(@PathVariable Long movieId, @RequestParam LocalDate showDate) {
        return ResponseEntity.ok(showService.getShowsByMovie(movieId, showDate));
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<ShowResponse>> getShowsByScreen(@PathVariable Long screenId) {
        return ResponseEntity.ok(showService.getShowsByScreen(screenId));
    }

    @GetMapping("/city/{city}/movie/{movieId}")
    public ResponseEntity<List<ShowResponse>> getShowsByCityAndMovieAndDate(@PathVariable String city, @PathVariable Long movieId, @RequestParam LocalDate showDate) {
        return ResponseEntity.ok(showService.getShowsByCityAndMovieAndDate(city, movieId, showDate));
    }

}