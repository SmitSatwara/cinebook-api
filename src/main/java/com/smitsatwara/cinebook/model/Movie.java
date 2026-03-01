package com.smitsatwara.cinebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private String language;
    @Column(nullable = false)
    private Integer duration;
    @Column
    private Double rating;
    @Column(nullable = false)
    private LocalDate releaseDate;
    @Column
    private String description;


}
