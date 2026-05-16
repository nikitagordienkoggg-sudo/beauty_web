package com.example.booking.mediator.interfaces;

import com.example.booking.entity.Schedule;
import com.example.booking.entity.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public interface IScheduleService {

    Schedule createSchedule(Long masterId, LocalDate date);

    List<Schedule> getScheduleByMaster(Long masterId);

    List<TimeSlot> getAvailableSlots(Long scheduleId);

    void addTimeSlot(Long scheduleId, TimeSlot slot);

    void updateSlotAvailability(Long slotId, boolean available);
}