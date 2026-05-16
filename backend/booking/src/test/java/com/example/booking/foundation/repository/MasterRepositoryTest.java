package com.example.booking.foundation.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.booking.entity.Master;
import com.example.booking.entity.Role;
import com.example.booking.entity.Salon;
import com.example.booking.foundation.repository.impl.MasterRepositoryImpl;
import com.example.booking.foundation.repository.impl.SalonRepositoryImpl;

@SpringBootTest
@ActiveProfiles("test")
@Import({MasterRepositoryImpl.class, SalonRepositoryImpl.class})
class MasterRepositoryTest {

    @Autowired
    IMasterRepository masterRepository;

    @Autowired
    ISalonRepository salonRepository;

    @Test
    void shouldSaveAndFindMasterBySalonId() {
        Salon salon = new Salon();
        salon.setName("Salon A");
        salon.setAddress("Addr");
        salonRepository.save(salon);

        Master master = new Master("Jane", "jane@test.com", "123", "Hair");
        master.setPassword("pwd");
        master.setRole(Role.ROLE_MASTER);
        master.setSalon(salon);
        Master saved = masterRepository.save(master);

        assertNotNull(saved.getId());
        assertTrue(masterRepository.findBySalonId(salon.getId()).stream().anyMatch(m -> m.getId().equals(saved.getId())));
    }

    @Test
    void shouldDeleteMasterById() {
        Master master = new Master("John", "john@test.com", "555", "Nails");
        master.setPassword("pwd");
        master.setRole(Role.ROLE_MASTER);
        Master saved = masterRepository.save(master);

        masterRepository.deleteById(saved.getId());

        assertFalse(masterRepository.findById(saved.getId()).isPresent());
    }
}
