package com.smitsatwara.cinebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "screens")
public class Screen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screenId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer totalSeats;
    @ManyToOne
    @JoinColumn(name = "theatre_id",nullable = false)
    private Theatre theatre;
}
