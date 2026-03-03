package com.smitsatwara.cinebook.dto;

import com.smitsatwara.cinebook.model.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class SeatRequest {
    @NotBlank
    private String seatNumber;
    @NotNull
    private SeatType seatType;
    @NotNull
    private Long screenId;
}
