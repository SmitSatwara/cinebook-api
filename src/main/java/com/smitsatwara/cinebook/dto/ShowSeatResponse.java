package com.smitsatwara.cinebook.dto;

import com.smitsatwara.cinebook.model.SeatStatus;
import com.smitsatwara.cinebook.model.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class ShowSeatResponse {

    private Long showSeatId;
    private Long seatId;
    private String seatNumber;
    private SeatType seatType;
    private double price;
    private SeatStatus status;
}
