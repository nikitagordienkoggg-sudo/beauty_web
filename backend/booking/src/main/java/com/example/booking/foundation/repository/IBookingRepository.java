package com.example.booking.foundation.repository;

import com.example.booking.entity.Booking;

import java.util.List;
import java.util.Optional;

public interface IBookingRepository {

    Booking save(Booking booking);

    Optional<Booking> findById(Long id);

    List<Booking> findAll();

    void deleteById(Long id);

    List<Booking> findByClientId(Long clientId);

    List<Booking> findByStatus(String status);
}