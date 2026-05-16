package com.example.booking.mediator.interfaces;

import com.example.booking.entity.Payment;

public interface IPaymentService {

    Payment processPayment(Long bookingId, double amount);

    void refundPayment(Long paymentId);
}