package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.ScreenRequest;
import com.smitsatwara.cinebook.model.Screen;
import com.smitsatwara.cinebook.model.Theatre;
import com.smitsatwara.cinebook.repository.ScreenRepository;
import com.smitsatwara.cinebook.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    //add screen to theatre
    public Screen addScreen(ScreenRequest screenRequest) {
        if(screenRepository.findByNameAndTheatreTheatreId(
                screenRequest.getName(),
                screenRequest.getTheatreId()).isPresent()) {
            throw new RuntimeException("Screen already exists with name: " + screenRequest.getName() + " in theatre with id: " + screenRequest.getTheatreId());
        }
        Screen screen = new Screen();
        Theatre theatre = theatreRepository.findById(screenRequest.getTheatreId())
                .orElseThrow(() -> new RuntimeException("Theatre not found with id: " + screenRequest.getTheatreId()));
       screen.setName(screenRequest.getName());
       screen.setTheatre(theatre);
       screen.setTotalSeats(screenRequest.getTotalSeats());
         return screenRepository.save(screen);
    }

    public List<Screen> getScreensByTheatreId(Long theatreId) {
        return screenRepository.findByTheatreTheatreId(theatreId);
    }



}
