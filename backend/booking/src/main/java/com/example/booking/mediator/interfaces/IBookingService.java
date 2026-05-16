package com.example.booking.mediator.interfaces;

import com.example.booking.entity.Booking;

import java.util.List;

public interface IBookingService {

    Booking createBooking(Long clientId, Long serviceId, Long masterId, Long timeSlotId);

    List<Booking> getBookingsByClient(Long clientId);

    void cancelBooking(Long bookingId);

    void confirmBooking(Long bookingId);
}