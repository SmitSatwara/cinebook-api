package com.smitsatwara.cinebook.service;

import com.smitsatwara.cinebook.dto.TheatreRequest;
import com.smitsatwara.cinebook.model.Theatre;
import com.smitsatwara.cinebook.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreService {
    private final TheatreRepository theatreRepository;

    public Theatre addTheatre(TheatreRequest theatreRequest) {
        if(theatreRepository.findByNameAndCityAndAddress(
                theatreRequest.getName(),
                theatreRequest.getCity(),
                theatreRequest.getAddress()).isPresent()) {
            throw new RuntimeException("Theatre already exists with name: " + theatreRequest.getName() + ", city: " + theatreRequest.getCity() + " and address: " + theatreRequest.getAddress());
        };
        Theatre theatre = new Theatre();
        theatre.setName(theatreRequest.getName());
        theatre.setCity(theatreRequest.getCity());
        theatre.setAddress(theatreRequest.getAddress());
        return  theatreRepository.save(theatre);
    }

    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }
    public List<Theatre> getTheatresByCity(String city){
        return theatreRepository.findByCity(city);
    }

    public List<Theatre> getTheatresByNameAndCity(String name, String city) {
        return theatreRepository.findByNameAndCity(name, city);
    }

}
