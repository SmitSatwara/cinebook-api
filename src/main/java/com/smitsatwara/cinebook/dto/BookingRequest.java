package com.smitsatwara.cinebook.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class BookingRequest {
    @NotNull
    private Long showId;
    @NotNull
    @Size(min = 1, message = "At least one seat must be selected")
    private List<Long> seatIds;
}
