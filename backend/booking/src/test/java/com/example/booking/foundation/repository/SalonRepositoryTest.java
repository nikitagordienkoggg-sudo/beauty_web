package com.example.booking.foundation.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.booking.entity.Salon;
import com.example.booking.foundation.repository.impl.SalonRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(SalonRepositoryImpl.class)
class SalonRepositoryTest {

    @Autowired
    ISalonRepository salonRepository;

    @Test
    void shouldSaveAndFindByNameContaining() {
        Salon salon = new Salon();
        salon.setName("Glow Beauty House");
        salon.setAddress("Address 1");

        Salon saved = salonRepository.save(salon);

        assertNotNull(saved.getId());
        assertTrue(salonRepository.findByNameContaining("beauty").stream().anyMatch(s -> s.getId().equals(saved.getId())));
    }

    @Test
    void shouldDeleteSalonById() {
        Salon salon = new Salon();
        salon.setName("Delete Me");
        salon.setAddress("Address 2");

        Salon saved = salonRepository.save(salon);
        salonRepository.deleteById(saved.getId());

        assertFalse(salonRepository.findById(saved.getId()).isPresent());
    }
}
