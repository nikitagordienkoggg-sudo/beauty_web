package com.example.booking.foundation.repository;

import com.example.booking.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface IScheduleRepository {

    Schedule save(Schedule schedule);

    Optional<Schedule> findById(Long id);

    List<Schedule> findAll();

    void deleteById(Long id);

    List<Schedule> findByMasterId(Long masterId);

    List<Schedule> findByDate(java.time.LocalDate date);
}