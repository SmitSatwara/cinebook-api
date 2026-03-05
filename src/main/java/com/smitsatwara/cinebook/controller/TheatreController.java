package com.smitsatwara.cinebook.controller;

import com.smitsatwara.cinebook.dto.TheatreRequest;
import com.smitsatwara.cinebook.model.Theatre;
import com.smitsatwara.cinebook.service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
@RequiredArgsConstructor
public class TheatreController {
    private final TheatreService theatreService;

    @PostMapping
    public ResponseEntity<Theatre> addTheatre(@RequestBody @Valid TheatreRequest theatreRequest) {
        return ResponseEntity.ok(theatreService.addTheatre(theatreRequest));
    }
    @GetMapping
    public ResponseEntity<List<Theatre>> getAllTheatres() {
        return ResponseEntity.ok(theatreService.getAllTheatres());
    }
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Theatre>> getTheatresByCity(@PathVariable String city) {
        return ResponseEntity.ok(theatreService.getTheatresByCity(city));
    }
    @GetMapping("/search")
    public ResponseEntity<List<Theatre>> getTheatreByNameAndCity(@RequestParam String name,@RequestParam String city) {
        return ResponseEntity.ok(theatreService.getTheatresByNameAndCity(name, city));
    }
}
