package com.smitsatwara.cinebook.controller;

import com.smitsatwara.cinebook.dto.ShowRequest;
import com.smitsatwara.cinebook.model.Show;
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
    public ResponseEntity<Show> addShow(@RequestBody @Valid ShowRequest showRequest) {
        return ResponseEntity.ok(showService.addShow(showRequest));
    }
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Show>> getShowByMovie(@PathVariable Long movieId, @RequestParam LocalDate showDate) {
        return ResponseEntity.ok(showService.getShowByMovie(movieId, showDate));
    }
    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<Show>> getShowByScreen(@PathVariable Long screenId){
        return ResponseEntity.ok(showService.getShowByScreen(screenId));
    }
}
