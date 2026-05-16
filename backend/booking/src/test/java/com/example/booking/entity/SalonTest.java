package com.example.booking.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SalonTest {

    @Test
    void shouldAddAndRemoveService() {
        Salon salon = new Salon();
        ServiceEntity service = new ServiceEntity();

        salon.addService(service);
        assertTrue(salon.getServices().contains(service));

        salon.removeService(service);
        assertFalse(salon.getServices().contains(service));
    }

    @Test
    void shouldUpdateBasicFields() {
        Salon salon = new Salon();

        salon.setName("Beauty Space");
        salon.setAddress("Main street 1");
        salon.setRating(4.8);

        assertEquals("Beauty Space", salon.getName());
        assertEquals("Main street 1", salon.getAddress());
        assertEquals(4.8, salon.getRating());
    }
}
