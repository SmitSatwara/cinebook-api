package com.smitsatwara.cinebook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class ShowResponse {
    private Long showId;
    private String movieTitle;
    private String theaterName;
    private String screenName;
    private LocalDate showDate;
    private LocalTime showTime;
    private Double price;
}
