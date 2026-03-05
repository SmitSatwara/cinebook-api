package com.smitsatwara.cinebook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class ScreenResponse {
    private Long screenId;
    private String screenName;
    private String theaterName;
    private String city;
}
