package com.myproject.movie.models.entities;

import com.myproject.movie.models.enums.PaymentMethod;
import com.myproject.movie.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Float amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime paymentTime;

    @Column(unique = true)
    private String transactionId;

    private String paymentGateway;
    private String qrCodeUrl;
    private String clientSecret;

    @PrePersist
    public void prePersist() {
        this.paymentTime = LocalDateTime.now();
    }
}
