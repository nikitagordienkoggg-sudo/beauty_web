package com.example.booking.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class ScheduleTest {

    @Test
    void shouldAddAndRemoveTimeSlotWithBidirectionalLink() {
        Schedule schedule = new Schedule();
        TimeSlot slot = new TimeSlot();
        slot.setStartTime(LocalTime.of(10, 0));
        slot.setEndTime(LocalTime.of(11, 0));

        schedule.addTimeSlot(slot);

        assertTrue(schedule.getTimeSlots().contains(slot));
        assertEquals(schedule, slot.getSchedule());

        schedule.removeTimeSlot(slot);

        assertTrue(schedule.getTimeSlots().isEmpty());
        assertNull(slot.getSchedule());
    }

    @Test
    void shouldSetDate() {
        Schedule schedule = new Schedule();
        LocalDate date = LocalDate.of(2026, 5, 16);

        schedule.setDate(date);

        assertEquals(date, schedule.getDate());
    }
}
