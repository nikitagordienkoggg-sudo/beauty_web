package com.example.booking.mediator.service;

import com.example.booking.entity.Master;
import com.example.booking.entity.Schedule;
import com.example.booking.entity.TimeSlot;
import com.example.booking.foundation.repository.IMasterRepository;
import com.example.booking.foundation.repository.IScheduleRepository;
import com.example.booking.foundation.repository.ITimeSlotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {
    @Mock IScheduleRepository scheduleRepository;
    @Mock IMasterRepository masterRepository;
    @Mock ITimeSlotRepository timeSlotRepository;

    @InjectMocks ScheduleServiceImpl service;

    @Test
    void createAndGetSchedule() {
        Master master = new Master();
        when(masterRepository.findById(1L)).thenReturn(Optional.of(master));
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(i -> i.getArgument(0));

        Schedule schedule = service.createSchedule(1L, LocalDate.of(2026, 1, 1));
        assertEquals(master, schedule.getMaster());
        assertEquals(LocalDate.of(2026, 1, 1), schedule.getDate());

        List<Schedule> schedules = List.of(schedule);
        when(scheduleRepository.findByMasterId(1L)).thenReturn(schedules);
        assertSame(schedules, service.getScheduleByMaster(1L));
    }

    @Test
    void getAvailableSlotsAndUpdateAvailability() {
        TimeSlot available = new TimeSlot();
        available.setAvailable(true);
        TimeSlot busy = new TimeSlot();
        busy.setAvailable(false);
        when(timeSlotRepository.findByScheduleId(1L)).thenReturn(List.of(available, busy));

        List<TimeSlot> result = service.getAvailableSlots(1L);
        assertEquals(1, result.size());
        assertSame(available, result.get(0));

        when(timeSlotRepository.findById(2L)).thenReturn(Optional.of(busy));
        service.updateSlotAvailability(2L, true);
        assertEquals(true, busy.isAvailable());
        verify(timeSlotRepository).save(busy);
    }

    @Test
    void addTimeSlotAndNotFoundBranches() {
        Schedule schedule = new Schedule();
        TimeSlot slot = new TimeSlot();
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        service.addTimeSlot(1L, slot);
        assertEquals(true, slot.isAvailable());
        verify(timeSlotRepository).save(slot);
        verify(scheduleRepository).save(schedule);

        when(masterRepository.findById(99L)).thenReturn(Optional.empty());
        when(scheduleRepository.findById(99L)).thenReturn(Optional.empty());
        when(timeSlotRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.createSchedule(99L, LocalDate.now()));
        assertThrows(RuntimeException.class, () -> service.addTimeSlot(99L, new TimeSlot()));
        assertThrows(RuntimeException.class, () -> service.updateSlotAvailability(99L, true));
    }
}
