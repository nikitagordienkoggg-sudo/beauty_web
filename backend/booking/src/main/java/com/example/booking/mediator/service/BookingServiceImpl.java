package com.example.booking.mediator.service;

import com.example.booking.entity.*;
import com.example.booking.foundation.repository.*;
import com.example.booking.mediator.interfaces.IBookingService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements IBookingService {

    private final IBookingRepository bookingRepository;
    private final IClientRepository clientRepository;
    private final IServiceEntityRepository serviceRepository;
    private final IMasterRepository masterRepository;
    private final ITimeSlotRepository timeSlotRepository;

    public BookingServiceImpl(
            IBookingRepository bookingRepository,
            IClientRepository clientRepository,
            IServiceEntityRepository serviceRepository,
            IMasterRepository masterRepository,
            ITimeSlotRepository timeSlotRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.clientRepository = clientRepository;
        this.serviceRepository = serviceRepository;
        this.masterRepository = masterRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    // ========================= UC-003 CREATE BOOKING =========================

    @Override
    public Booking createBooking(Long clientId, Long serviceId, Long masterId, Long timeSlotId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new RuntimeException("Master not found"));

        TimeSlot slot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new RuntimeException("TimeSlot not found"));

        if (!slot.isAvailable()) {
            throw new RuntimeException("Time slot is not available");
        }

        slot.reserve();
        timeSlotRepository.save(slot);

        Booking booking = new Booking(
                LocalDateTime.now(),
                "CREATED"
        );

        booking.setClient(client);
        booking.setService(service);
        booking.setMaster(master);

        return bookingRepository.save(booking);
    }

    // ========================= UC-005 GET BOOKINGS =========================

    @Override
    public List<Booking> getBookingsByClient(Long clientId) {
        return bookingRepository.findByClientId(clientId);
    }

    // ========================= UC-005 CANCEL BOOKING =========================

    @Override
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.canBeCancelled()) {
            throw new RuntimeException("Booking cannot be cancelled");
        }

        booking.updateStatus("CANCELLED");
        bookingRepository.save(booking);
    }

    // ========================= UC-009 CONFIRM BOOKING =========================

    @Override
    public void confirmBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.updateStatus("CONFIRMED");

        bookingRepository.save(booking);
    }
}