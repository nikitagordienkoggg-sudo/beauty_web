package com.example.booking.mediator.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Payment;
import com.example.booking.external.ExternalPaymentGateway;
import com.example.booking.foundation.repository.IBookingRepository;
import com.example.booking.foundation.repository.IPaymentRepository;
import com.example.booking.mediator.interfaces.IPaymentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentServiceImpl implements IPaymentService {

    private final IPaymentRepository paymentRepository;
    private final IBookingRepository bookingRepository;
    private final ExternalPaymentGateway paymentGateway;

    public PaymentServiceImpl(
            IPaymentRepository paymentRepository,
            IBookingRepository bookingRepository,
            ExternalPaymentGateway paymentGateway
    ) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.paymentGateway = paymentGateway;
    }

    // ========================= UC-04 PAYMENT =========================

    @Override
    public Payment processPayment(Long bookingId, double amount) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        boolean success;

        try {
            success = paymentGateway.pay(amount);
        } catch (Exception e) {
            throw new RuntimeException("Payment service unavailable");
        }

        if (!success) {
            throw new RuntimeException("Payment failed");
        }

        Payment payment = new Payment(amount, "SUCCESS");
        payment.setBooking(booking);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    // ========================= UC-16 REFUND =========================

    @Override
    public void refundPayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        try {
            paymentGateway.refund(payment.getAmount());
        } catch (Exception e) {
            throw new RuntimeException("Refund service unavailable");
        }

        payment.refund();
        paymentRepository.save(payment);
    }
}