package com.example.booking.control.controller;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.booking.entity.Payment;
import com.example.booking.mediator.interfaces.IPaymentService;
import com.example.booking.security.JwtAuthenticationFilter;


@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {

    @Autowired MockMvc mockMvc;

    @MockBean IPaymentService paymentService;
    @MockBean JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean UserDetailsService userDetailsService;

    @Test
    void shouldProcessPayment() throws Exception {

        Payment payment = new Payment(100, "SUCCESS");

        when(paymentService.processPayment(1L, 100))
                .thenReturn(payment);

        mockMvc.perform(post("/api/payments")
            .param("bookingId", "1")
            .param("amount", "100"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRefundPayment() throws Exception {

        mockMvc.perform(post("/api/payments/1/refund"))
                .andExpect(status().isOk());
    }
}
