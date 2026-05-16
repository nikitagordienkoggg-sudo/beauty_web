package com.example.booking.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private Master master;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<TimeSlot> timeSlots = new ArrayList<>();

    public Schedule() {}

    // ===== BUSINESS METHODS =====

    public void addTimeSlot(TimeSlot slot) {
        timeSlots.add(slot);
        slot.setSchedule(this);
    }

    public void removeTimeSlot(TimeSlot slot) {
        timeSlots.remove(slot);
        slot.setSchedule(null);
    }

    // ===== GETTERS / SETTERS =====

    public Long getId() { return id; }

    public LocalDate getDate() { return date; }

    public Master getMaster() { return master; }

    public List<TimeSlot> getTimeSlots() { return timeSlots; }

    public void setDate(LocalDate date) { this.date = date; }

    public void setMaster(Master master) { this.master = master; }

    public void setTimeSlots(List<TimeSlot> timeSlots) { this.timeSlots = timeSlots; }
}