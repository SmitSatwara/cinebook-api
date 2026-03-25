package com.smitsatwara.cinebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter@Setter@NoArgsConstructor
@Table(
        name = "bookings",
        indexes = {
                @Index(name = "idx_booking_user_id", columnList = "user_id")
        }
)
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;
    @Column(nullable = false)
    private Double totalAmount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;
    @Column(nullable = false,updatable = false)
    private LocalDateTime bookedAt;

    @PrePersist
    protected void onBook() {
        bookedAt = LocalDateTime.now();
    }

}
