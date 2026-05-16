package com.example.booking.external;

import org.springframework.stereotype.Component;

@Component
public class ExternalPaymentGateway {

    public boolean pay(double amount) {
        // имитация внешнего платежного API
        return amount > 0;
    }

    public void refund(double amount) {
        // имитация refund API
    }
}