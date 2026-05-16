package com.example.booking.foundation.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.booking.entity.Master;
import com.example.booking.entity.Schedule;
import com.example.booking.foundation.repository.impl.MasterRepositoryImpl;
import com.example.booking.foundation.repository.impl.ScheduleRepositoryImpl;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import({ScheduleRepositoryImpl.class, MasterRepositoryImpl.class})
class ScheduleRepositoryTest {

    @Autowired
    IScheduleRepository scheduleRepository;

    @Autowired
    IMasterRepository masterRepository;

    @Test
    void shouldSaveAndFindByMasterAndDate() {
        Master master = new Master("Ann", "ann@test.com", "321", "Style");
        masterRepository.save(master);

        Schedule schedule = new Schedule();
        LocalDate date = LocalDate.of(2026, 5, 17);
        schedule.setDate(date);
        schedule.setMaster(master);

        Schedule saved = scheduleRepository.save(schedule);

        assertNotNull(saved.getId());
        assertTrue(scheduleRepository.findByMasterId(master.getId()).stream().anyMatch(s -> s.getId().equals(saved.getId())));
        assertTrue(scheduleRepository.findByDate(date).stream().anyMatch(s -> s.getId().equals(saved.getId())));
    }

    @Test
    void shouldDeleteScheduleById() {
        Schedule schedule = new Schedule();
        schedule.setDate(LocalDate.of(2026, 5, 18));

        Schedule saved = scheduleRepository.save(schedule);
        scheduleRepository.deleteById(saved.getId());

        assertFalse(scheduleRepository.findById(saved.getId()).isPresent());
    }
}
