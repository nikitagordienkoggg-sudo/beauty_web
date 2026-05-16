package com.example.booking.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceEntityTest {

    @Test
    void shouldUpdateDetails() {

        ServiceEntity s = new ServiceEntity();

        s.updateDetails("Haircut", "desc", 30, 100);

        assertEquals("Haircut", s.getName());
        assertEquals(100, s.getPrice());
    }
}