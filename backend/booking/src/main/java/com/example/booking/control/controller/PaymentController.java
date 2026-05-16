package com.example.booking.control.controller;

import com.example.booking.entity.Payment;
import com.example.booking.mediator.interfaces.IPaymentService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // UC-04
    @PostMapping
    public Payment pay(@RequestParam Long bookingId,
                       @RequestParam double amount) {
        return paymentService.processPayment(bookingId, amount);
    }

    // UC-16
    @PostMapping("/{id}/refund")
    public void refund(@PathVariable Long id) {
        paymentService.refundPayment(id);
    }
}