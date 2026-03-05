package com.smitsatwara.cinebook.dto;

import com.smitsatwara.cinebook.model.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter@Setter@AllArgsConstructor @NoArgsConstructor
public class MovieRequest {

    @NotBlank
    private  String title;
    @NotBlank
    private String genre;
    @NotNull
    private Language language;
    @NotNull
    private Integer duration;
    private Double rating;
    @NotNull
    private LocalDate releaseDate;
    private String description;
}
