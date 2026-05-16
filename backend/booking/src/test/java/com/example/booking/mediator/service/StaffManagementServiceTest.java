package com.example.booking.mediator.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Master;
import com.example.booking.entity.TimeSlot;
import com.example.booking.foundation.repository.IBookingRepository;
import com.example.booking.foundation.repository.IMasterRepository;
import com.example.booking.foundation.repository.ITimeSlotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StaffManagementServiceTest {
    @Mock IMasterRepository masterRepository;
    @Mock ITimeSlotRepository timeSlotRepository;
    @Mock IBookingRepository bookingRepository;

    @InjectMocks StaffManagementServiceImpl service;

    @Test
    void updateMasterAvailabilityAndCompletion() {
        Master existing = new Master();
        Master update = new Master();
        update.setSpecialization("coloring");
        when(masterRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(masterRepository.save(existing)).thenReturn(existing);

        Master result = service.updateMaster(1L, update);
        assertEquals("coloring", result.getSpecialization());

        TimeSlot slot = new TimeSlot();
        when(timeSlotRepository.findById(2L)).thenReturn(Optional.of(slot));
        service.updateAvailability(1L, 2L, false);
        assertEquals(false, slot.isAvailable());

        Booking booking = new Booking();
        when(bookingRepository.findById(3L)).thenReturn(Optional.of(booking));
        service.confirmServiceCompletion(3L);
        assertEquals("COMPLETED", booking.getStatus());
        verify(bookingRepository).save(booking);
    }

    @Test
    void notFoundBranches() {
        when(masterRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.updateMaster(10L, new Master()));

        when(masterRepository.findById(1L)).thenReturn(Optional.of(new Master()));
        when(timeSlotRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.updateAvailability(1L, 10L, true));

        when(bookingRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.confirmServiceCompletion(10L));
    }
}
