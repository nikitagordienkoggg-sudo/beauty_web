package com.example.booking.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "master")
@PrimaryKeyJoinColumn(name = "id")
public class Master extends User {

    @Column(nullable = false)
    private String specialization;

    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;

    @OneToMany(mappedBy = "master")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "master")
    private List<Schedule> schedules = new ArrayList<>();

    public Master() {
    }

    public Master(
            String name,
            String email,
            String phone,
            String specialization
    ) {
        super(name, email, phone);
        this.specialization = specialization;
    }

    // ===== BUSINESS METHODS =====

    public void updateAvailability(
            TimeSlot slot,
            boolean available
    ) {
        slot.setAvailable(available);
    }

    public void completeBooking(Booking booking) {
        booking.updateStatus("COMPLETED");
    }

    // ===== GETTERS / SETTERS =====

    public String getSpecialization() {
        return specialization;
    }

    public Salon getSalon() {
        return salon;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}