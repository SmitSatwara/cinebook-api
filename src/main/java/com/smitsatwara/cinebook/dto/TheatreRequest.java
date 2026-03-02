package com.smitsatwara.cinebook.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class TheatreRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String address;
}
