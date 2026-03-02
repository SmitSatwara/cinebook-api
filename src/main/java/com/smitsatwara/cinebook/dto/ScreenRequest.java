package com.smitsatwara.cinebook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class ScreenRequest {
    @NotBlank
    private String name;
    @NotNull
    private Integer totalSeats;
    @NotNull
    private Long theatreId;
}
