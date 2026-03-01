package com.smitsatwara.cinebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "show_seats")
public class ShowSeat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showSeatId;
    @ManyToOne
    @JoinColumn(name = "show_id",nullable = false)
    private Show show;
    @ManyToOne
    @JoinColumn(name = "seat_id",nullable = false)
    private Seat seat;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;


}
