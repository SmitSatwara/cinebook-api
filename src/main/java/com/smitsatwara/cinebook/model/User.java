package com.smitsatwara.cinebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    @Column(nullable = false)
    String userName;
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false)
    String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserRole role;
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
