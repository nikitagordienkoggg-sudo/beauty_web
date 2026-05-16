package com.example.booking.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "timeslot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "is_available")
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public TimeSlot() {
    }

    // ===== BUSINESS METHODS =====

    public boolean reserve() {

        if (!available) {
            return false;
        }

        this.available = false;
        return true;
    }

    public void release() {
        this.available = true;
    }

    public boolean isAvailable() {
        return available;
    }

    // ===== GETTERS / SETTERS =====

    public Long getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}