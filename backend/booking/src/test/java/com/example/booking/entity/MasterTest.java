package com.example.booking.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MasterTest {

    @Test
    void shouldUpdateSpecialization() {

        Master m = new Master();
        m.setSpecialization("Hair");

        assertEquals("Hair", m.getSpecialization());
    }
}