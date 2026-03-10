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
@Table(name = "movies", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "language"})
})
public class Movie {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String genre;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;
    @Column(nullable = false)
    private Integer duration;
    @Column
    private Double rating;
    @Column(nullable = false)
    private LocalDate releaseDate;
    @Column
    private String description;


}
