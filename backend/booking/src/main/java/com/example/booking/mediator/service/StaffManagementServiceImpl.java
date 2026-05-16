package com.example.booking.mediator.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Master;
import com.example.booking.entity.TimeSlot;
import com.example.booking.foundation.repository.IBookingRepository;
import com.example.booking.foundation.repository.IMasterRepository;
import com.example.booking.foundation.repository.ITimeSlotRepository;
import com.example.booking.mediator.interfaces.IStaffManagementService;

@Service
@Transactional
public class StaffManagementServiceImpl implements IStaffManagementService {

    private final IMasterRepository masterRepository;
    private final ITimeSlotRepository timeSlotRepository;
    private final IBookingRepository bookingRepository;

    public StaffManagementServiceImpl(
            IMasterRepository masterRepository,
            ITimeSlotRepository timeSlotRepository,
            IBookingRepository bookingRepository
    ) {
        this.masterRepository = masterRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.bookingRepository = bookingRepository;
    }

    // ========================= UC-06 =========================

    @Override
    public Master createMaster(Master master) {
        return masterRepository.save(master);
    }

    // ========================= UC-07 =========================

    @Override
    public Master updateMaster(Long id, Master master) {

        Master existing = masterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Master not found"));

        existing.setSpecialization(master.getSpecialization());

        return masterRepository.save(existing);
    }

    // ========================= UC-10 =========================

    @Override
    public void updateAvailability(Long masterId, Long slotId, boolean available) {

        masterRepository.findById(masterId)
                .orElseThrow(() -> new RuntimeException("Master not found"));

        TimeSlot slot = timeSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        slot.setAvailable(available);

        timeSlotRepository.save(slot);
    }

    // ========================= UC-11 =========================

    @Override
    public void confirmServiceCompletion(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.updateStatus("COMPLETED");

        bookingRepository.save(booking);
    }
}