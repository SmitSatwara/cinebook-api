package com.smitsatwara.cinebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "shows",
        indexes = {
                @Index(name = "idx_shows_movie_id", columnList = "movie_id"),
                @Index(name = "idx_show_screen_id", columnList = "screen_id")
        }
)
public class Show {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;
    @ManyToOne
    @JoinColumn(name = "movie_id",nullable = false)
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "screen_id",nullable = false)
    private Screen screen;
    @Column(nullable = false)
    private LocalDate showDate;
    @Column(nullable = false)
    private LocalTime showTime;
    @Column
    private Double price;

}
