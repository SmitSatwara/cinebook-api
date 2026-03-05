package com.smitsatwara.cinebook.dto;

import com.smitsatwara.cinebook.model.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class SeatResponse {
    private Long seatId;
    private String seatNumber;
    private SeatType seatType;
    private String screenName;
    private String theaterName;
    private String city;
}
