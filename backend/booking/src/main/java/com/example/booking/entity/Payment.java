package com.example.booking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String status;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @OneToOne
    @JoinColumn(name = "booking_id", unique = true)
    private Booking booking;

    public Payment() {
    }

    public Payment(double amount, String status) {
        this.amount = amount;
        this.status = status;
        this.paymentDate = LocalDateTime.now();
    }

    // ===== BUSINESS METHODS =====

    public boolean process() {

        this.status = "PAID";
        this.paymentDate = LocalDateTime.now();

        return true;
    }

    public boolean refund() {

        if (!"PAID".equals(status)) {
            return false;
        }

        this.status = "REFUNDED";

        return true;
    }

    // ===== GETTERS / SETTERS =====

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}