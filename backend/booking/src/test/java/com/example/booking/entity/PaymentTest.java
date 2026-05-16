package com.example.booking.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void shouldRefundPayment() {

        Payment payment = new Payment(100, "PAID");
        payment.refund();

        assertEquals("REFUNDED", payment.getStatus());
    }
}