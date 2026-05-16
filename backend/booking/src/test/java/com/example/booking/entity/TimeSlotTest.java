package com.example.booking.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    @Test
    void shouldReserveSlot() {

        TimeSlot slot = new TimeSlot();
        slot.reserve();

        assertFalse(slot.isAvailable());
    }

    @Test
    void shouldReleaseSlot() {

        TimeSlot slot = new TimeSlot();
        slot.reserve();
        slot.release();

        assertTrue(slot.isAvailable());
    }

    @Test
    void shouldToggleAvailability() {

        TimeSlot slot = new TimeSlot();

        slot.reserve();
        assertFalse(slot.isAvailable());

        slot.release();
        assertTrue(slot.isAvailable());
    }
}