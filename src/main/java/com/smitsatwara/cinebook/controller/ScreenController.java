package com.smitsatwara.cinebook.controller;

import com.smitsatwara.cinebook.dto.ScreenRequest;
import com.smitsatwara.cinebook.dto.ScreenResponse;
import com.smitsatwara.cinebook.service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/screens")
public class ScreenController {
    private final ScreenService screenService;

    @PostMapping
    public ResponseEntity<ScreenResponse> addScreen(@RequestBody @Valid ScreenRequest screenRequest) {
        return ResponseEntity.ok(screenService.addScreen(screenRequest));
    }
    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<ScreenResponse>> getScreensByTheatreId(@PathVariable Long theatreId) {
        return ResponseEntity.ok(screenService.getScreensByTheatreId(theatreId));
    }
}
