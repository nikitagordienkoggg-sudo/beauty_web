package com.example.booking.mediator.service;

import com.example.booking.entity.Booking;
import com.example.booking.foundation.repository.IBookingRepository;
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
class AdminServiceTest {

    @Mock
    private IBookingRepository bookingRepository;

    @InjectMocks
    private AdminServiceImpl service;

    @Test
    void resolveDisputeUpdatesStatus() {
        Booking booking = new Booking();
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        service.resolveDispute(1L);

        assertEquals("DISPUTE_RESOLVED", booking.getStatus());
        verify(bookingRepository).save(booking);
    }

    @Test
    void resolveDisputeThrowsWhenMissingBooking() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.resolveDispute(1L));
    }

    @Test
    void blockUserNotImplemented() {
        assertThrows(RuntimeException.class, () -> service.blockUser(1L));
    }

    @Test
    void unblockUserNotImplemented() {
        assertThrows(RuntimeException.class, () -> service.unblockUser(1L));
    }
}
