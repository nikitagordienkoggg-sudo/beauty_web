package com.example.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "service")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private double price;

    @ManyToOne()
    @JoinColumn(name = "salon_id")
    private Salon salon;

    public ServiceEntity() {
    }

    public ServiceEntity(
            String name,
            String description,
            int duration,
            double price
    ) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
    }

    // ===== BUSINESS METHODS =====

    public void updateDetails(
            String name,
            String description,
            int duration,
            double price
    ) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
    }

    // ===== GETTERS / SETTERS =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
}