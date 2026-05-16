package com.example.booking.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "id")
public class Client extends User {

    @Column(name = "loyalty_points")
    private int loyaltyPoints;

    @OneToMany(mappedBy = "client")
    private List<Booking> bookings = new ArrayList<>();

    public Client() {
    }

    public Client(String name, String email, String phone) {
        super(name, email, phone);
    }

    // ===== BUSINESS METHODS =====

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    public boolean canBookService() {
        return true;
    }

    // ===== GETTERS / SETTERS =====

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}