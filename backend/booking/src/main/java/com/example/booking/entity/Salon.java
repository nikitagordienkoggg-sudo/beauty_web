package com.example.booking.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "salon")
public class Salon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column
    private double rating;

    @OneToMany(mappedBy = "salon")
    private List<Master> masters = new ArrayList<>();

    @OneToMany(mappedBy = "salon")
    private List<ServiceEntity> services = new ArrayList<>();

    @OneToMany(mappedBy = "salon")
    private List<Booking> bookings = new ArrayList<>();

    public Salon() {}

    // ===== BUSINESS METHODS =====

    public void addService(ServiceEntity service) {
        services.add(service);
    }

    public void removeService(ServiceEntity service) {
        services.remove(service);
    }

    // ===== GETTERS / SETTERS =====

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getAddress() { return address; }

    public double getRating() { return rating; }

    public List<Master> getMasters() { return masters; }

    public List<ServiceEntity> getServices() { return services; }

    public List<Booking> getBookings() { return bookings; }

    public void setName(String name) { this.name = name; }

    public void setAddress(String address) { this.address = address; }

    public void setRating(double rating) { this.rating = rating; }
}