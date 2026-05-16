package com.example.booking.mediator.service;

import com.example.booking.entity.Master;
import com.example.booking.entity.Schedule;
import com.example.booking.entity.TimeSlot;
import com.example.booking.foundation.repository.IMasterRepository;
import com.example.booking.foundation.repository.IScheduleRepository;
import com.example.booking.foundation.repository.ITimeSlotRepository;
import com.example.booking.mediator.interfaces.IScheduleService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ScheduleServiceImpl implements IScheduleService {

    private final IScheduleRepository scheduleRepository;
    private final IMasterRepository masterRepository;
    private final ITimeSlotRepository timeSlotRepository;

    public ScheduleServiceImpl(
            IScheduleRepository scheduleRepository,
            IMasterRepository masterRepository,
            ITimeSlotRepository timeSlotRepository
    ) {
        this.scheduleRepository = scheduleRepository;
        this.masterRepository = masterRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    // ========================= UC-08 CREATE SCHEDULE =========================

    @Override
    public Schedule createSchedule(Long masterId, LocalDate date) {

        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new RuntimeException("Master not found"));

        Schedule schedule = new Schedule();
        schedule.setDate(date);
        schedule.setMaster(master);

        return scheduleRepository.save(schedule);
    }

    // ========================= UC-02 VIEW SCHEDULE =========================

    @Override
    public List<Schedule> getScheduleByMaster(Long masterId) {
        return scheduleRepository.findByMasterId(masterId);
    }

    // ========================= UC-10 GET AVAILABLE SLOTS =========================

    @Override
    public List<TimeSlot> getAvailableSlots(Long scheduleId) {

        return timeSlotRepository.findByScheduleId(scheduleId)
                .stream()
                .filter(TimeSlot::isAvailable)
                .toList();
    }

    // ========================= UC-10 ADD SLOT =========================

    @Override
    public void addTimeSlot(Long scheduleId, TimeSlot slot) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        slot.setAvailable(true);

        schedule.addTimeSlot(slot);

        timeSlotRepository.save(slot);
        scheduleRepository.save(schedule);
    }

    // ========================= UC-10 UPDATE SLOT =========================

    @Override
    public void updateSlotAvailability(Long slotId, boolean available) {

        TimeSlot slot = timeSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("TimeSlot not found"));

        slot.setAvailable(available);

        timeSlotRepository.save(slot);
    }
}