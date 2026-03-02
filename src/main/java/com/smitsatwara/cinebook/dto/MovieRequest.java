package com.smitsatwara.cinebook.dto;

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
    @NotBlank
    private String language;
    @NotNull
    private Integer duration;
    private Double rating;
    @NotNull
    private LocalDate releaseDate;
    private String description;
}
