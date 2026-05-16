package com.example.booking.foundation.repository;

import com.example.booking.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface IPaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findById(Long id);

    List<Payment> findAll();

    Optional<Payment> findByBookingId(Long bookingId);

    void deleteById(Long id);
}