package com.example.booking.foundation.repository;

import com.example.booking.entity.TimeSlot;

import java.util.List;
import java.util.Optional;

public interface ITimeSlotRepository {

    TimeSlot save(TimeSlot timeSlot);

    Optional<TimeSlot> findById(Long id);

    List<TimeSlot> findAll();

    void deleteById(Long id);

    List<TimeSlot> findByScheduleId(Long scheduleId);

    List<TimeSlot> findByAvailable(boolean available);
}