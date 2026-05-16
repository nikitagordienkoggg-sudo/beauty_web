package com.example.booking.mediator.service;

import java.util.Optional;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Payment;
import com.example.booking.external.ExternalPaymentGateway;
import com.example.booking.foundation.repository.*;
import com.example.booking.mediator.service.PaymentServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock IPaymentRepository paymentRepository;
    @Mock IBookingRepository bookingRepository;
    @Mock ExternalPaymentGateway gateway;

    @InjectMocks PaymentServiceImpl service;

   @Test
    void shouldProcessPayment() {
        // 1. Подготовка данных
        Booking booking = new com.example.booking.entity.Booking();
        when(bookingRepository.findById(1L)).thenReturn(java.util.Optional.of(booking));
        when(gateway.pay(anyDouble())).thenReturn(true);

        // 2. Настройка сохранения (чтобы не возвращался null)
        // Мы говорим: "Когда вызывается save, верни первый аргумент (сам платеж)"
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        // 3. Выполнение
        Payment payment = service.processPayment(1L, 100);

        // 4. Проверки
        assertNotNull(payment, "Платеж не должен быть null");
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(100, payment.getAmount());
        
        // Проверка, что репозиторий действительно вызывался
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void shouldRefundPayment() {

        Payment payment = new Payment(100, "PAID");

        when(paymentRepository.findById(1L))
                .thenReturn(java.util.Optional.of(payment));

        service.refundPayment(1L);

        assertEquals("REFUNDED", payment.getStatus());
    }

    @Test
    void shouldFailPayment_whenGatewayFails() {

        Booking booking = new Booking();

        when(bookingRepository.findById(1L))
                .thenReturn(Optional.of(booking));

        when(gateway.pay(anyDouble())).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> service.processPayment(1L, 100));
    }

}