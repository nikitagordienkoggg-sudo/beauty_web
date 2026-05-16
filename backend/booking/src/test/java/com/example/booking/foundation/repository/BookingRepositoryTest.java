package com.example.booking.foundation.repository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.booking.entity.Booking;
import com.example.booking.foundation.repository.impl.BookingRepositoryImpl;

@SpringBootTest
@ActiveProfiles("test")
@Import(BookingRepositoryImpl.class)
class BookingRepositoryTest {

    @Autowired IBookingRepository repo;

    @Test
    void shouldSaveBooking() {
        Booking b = new Booking(LocalDateTime.now(), "CREATED");
        // Если конструктора нет, используйте сеттеры для dateTime и status
        
        Booking saved = repo.save(b);
        
        assertNotNull(saved.getId());
        assertEquals("CREATED", saved.getStatus());
    }

    @Test
    void shouldFindByStatus() {

        Booking b = new Booking();
        b.setDateTime(LocalDateTime.now());
        b.setStatus("CREATED");
        repo.save(b);

        var list = repo.findByStatus("CREATED");

        assertFalse(list.isEmpty());
    }
    
    @Test
    void shouldFindById(){
        Booking b = new Booking();
        b.setDateTime(LocalDateTime.now());
        b.setStatus("CREATED");
        Booking saved = repo.save(b);

        var found = repo.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    void shouldDeleteById(){
        Booking b = new Booking();
        b.setDateTime(LocalDateTime.now());
        b.setStatus("CREATED");
        Booking saved = repo.save(b);

        repo.deleteById(saved.getId());

        var found = repo.findById(saved.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void shouldFindAll(){
        Booking b1 = new Booking();
        b1.setDateTime(LocalDateTime.now());
        b1.setStatus("CREATED");
        repo.save(b1);

        Booking b2 = new Booking();
        b2.setDateTime(LocalDateTime.now());
        b2.setStatus("CREATED");
        repo.save(b2);

        var list = repo.findAll();

        assertTrue(list.size() >= 2);
    }

}