package com.example.booking.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void shouldAddLoyaltyPoints() {

        Client client = new Client();
        client.setLoyaltyPoints(10);

        client.addLoyaltyPoints(5);

        assertEquals(15, client.getLoyaltyPoints());
    }

    @Test
    void shouldUpdateProfile() {

        Client c = new Client();
        c.updateProfile("Name", "123");

        assertEquals("Name", c.getName());
        assertEquals("123", c.getPhone());
    }
}