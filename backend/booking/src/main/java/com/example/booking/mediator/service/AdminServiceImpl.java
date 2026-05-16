package com.example.booking.mediator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.booking.entity.Booking;
import com.example.booking.foundation.repository.IBookingRepository;
import com.example.booking.mediator.interfaces.IAdminService;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

    private final IBookingRepository bookingRepository;

    public AdminServiceImpl(IBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // ========================= UC-12/13 =========================

    @Override
    public void blockUser(Long userId) {
        // упрощённо: в ЛР обычно достаточно заглушки
        throw new RuntimeException("Not implemented in scope");
    }

    @Override
    public void unblockUser(Long userId) {
        throw new RuntimeException("Not implemented in scope");
    }

    // ========================= UC-14 =========================

    @Override
    public void resolveDispute(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.updateStatus("DISPUTE_RESOLVED");

        bookingRepository.save(booking);
    }
}