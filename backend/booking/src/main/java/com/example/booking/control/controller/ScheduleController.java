package com.example.booking.control.controller;

import com.example.booking.entity.Schedule;
import com.example.booking.entity.TimeSlot;
import com.example.booking.mediator.interfaces.IScheduleService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final IScheduleService service;

    public ScheduleController(IScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public Schedule create(@RequestParam Long masterId,
                           @RequestParam String date) {
        return service.createSchedule(masterId, LocalDate.parse(date));
    }

    @GetMapping("/{masterId}")
    public List<Schedule> get(@PathVariable Long masterId) {
        return service.getScheduleByMaster(masterId);
    }

    @GetMapping("/slots/{scheduleId}")
    public List<TimeSlot> slots(@PathVariable Long scheduleId) {
        return service.getAvailableSlots(scheduleId);
    }
}