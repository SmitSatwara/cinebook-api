package com.smitsatwara.cinebook.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class ShowRequest {
    @NotNull
    private Long movieId;
    @NotNull
    private Long screenId;
    @NotNull
    private LocalDate showDate;
    @NotNull
    private LocalTime showTime;
    @NotNull
    private Double price;
}
