package com.smitsatwara.cinebook.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "seats")
public class Seat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    @Column(nullable = false)
    private String seatNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType seatType;
    @ManyToOne
    @JoinColumn(name = "screen_id",nullable = false)
    private Screen screen;

}
