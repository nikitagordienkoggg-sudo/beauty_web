package com.example.booking.entity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class BookingTest {

    @Test
    void shouldUpdateStatus() {

        Booking booking = new Booking();
        booking.updateStatus("CREATED");

        assertEquals("CREATED", booking.getStatus());
    }

    @Test
    void shouldAllowCancellationWhenNotCompleted() {

        Booking booking = new Booking();
        booking.updateStatus("CREATED");
                booking.setDateTime(LocalDateTime.now().plusDays(1));

        assertTrue(booking.canBeCancelled());
    }

    @Test
    void shouldNotAllowCancellationWhenCompleted() {

        Booking booking = new Booking();
        booking.updateStatus("COMPLETED");

        assertFalse(booking.canBeCancelled());
    }

    @Test
    void shouldUpdateMultipleFields() {

        Booking b = new Booking();
        b.updateStatus("CREATED");

        Client c = new Client();
        b.setClient(c);

        assertEquals("CREATED", b.getStatus());
    }

}