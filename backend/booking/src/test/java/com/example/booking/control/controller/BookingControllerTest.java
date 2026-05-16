package com.example.booking.control.controller;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.core.userdetails.UserDetailsService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.booking.entity.Booking;
import com.example.booking.mediator.interfaces.IBookingService;
import com.example.booking.security.JwtAuthenticationFilter;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnBookings() throws Exception {

        when(bookingService.getBookingsByClient(1L)).thenReturn(java.util.List.of());

        mockMvc.perform(get("/api/bookings/client/1"))
                .andExpect(status().isOk());
    }

    @MockBean
    private IBookingService bookingService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void shouldCreateBooking() throws Exception {

        Booking booking = new Booking();
        booking.updateStatus("CREATED");

        when(bookingService.createBooking(any(), any(), any(), any()))
                .thenReturn(booking);

        mockMvc.perform(post("/api/bookings")
                .contentType("application/json")
                .content("""
                    {
                    "clientId": 1,
                    "serviceId": 1,
                    "masterId": 1,
                    "timeSlotId": 1
                    }
                """))
                .andExpect(status().isOk());
    }

}