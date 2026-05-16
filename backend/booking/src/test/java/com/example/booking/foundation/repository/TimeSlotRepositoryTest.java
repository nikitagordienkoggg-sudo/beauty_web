package com.example.booking.foundation.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.booking.entity.Schedule;
import com.example.booking.entity.TimeSlot;
import com.example.booking.foundation.repository.impl.ScheduleRepositoryImpl;
import com.example.booking.foundation.repository.impl.TimeSlotRepositoryImpl;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import({TimeSlotRepositoryImpl.class, ScheduleRepositoryImpl.class})
class TimeSlotRepositoryTest {

    @Autowired
    ITimeSlotRepository timeSlotRepository;

    @Autowired
    IScheduleRepository scheduleRepository;

    @Test
    void shouldSaveAndFindByScheduleAndAvailability() {
        Schedule schedule = new Schedule();
        schedule.setDate(LocalDate.of(2026, 5, 19));
        scheduleRepository.save(schedule);

        TimeSlot slot = new TimeSlot();
        slot.setStartTime(LocalTime.of(9, 0));
        slot.setEndTime(LocalTime.of(10, 0));
        slot.setSchedule(schedule);
        slot.setAvailable(true);

        TimeSlot saved = timeSlotRepository.save(slot);

        assertNotNull(saved.getId());
        assertTrue(timeSlotRepository.findByScheduleId(schedule.getId()).stream().anyMatch(t -> t.getId().equals(saved.getId())));
        assertTrue(timeSlotRepository.findByAvailable(true).stream().anyMatch(t -> t.getId().equals(saved.getId())));
    }

    @Test
    void shouldDeleteTimeSlotById() {
        TimeSlot slot = new TimeSlot();
        slot.setStartTime(LocalTime.of(11, 0));
        slot.setEndTime(LocalTime.of(12, 0));

        TimeSlot saved = timeSlotRepository.save(slot);
        timeSlotRepository.deleteById(saved.getId());

        assertFalse(timeSlotRepository.findById(saved.getId()).isPresent());
    }
}
