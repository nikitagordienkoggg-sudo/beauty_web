package com.example.booking.control.controller;

import com.example.booking.control.dto.request.BookingRequest;
import com.example.booking.control.dto.response.BookingResponse;
import com.example.booking.entity.Booking;
import com.example.booking.mediator.interfaces.IBookingService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ================= UC-003 =================

    @PostMapping
    public BookingResponse create(@RequestBody BookingRequest request) {

        Booking booking = bookingService.createBooking(
                request.clientId,
                request.serviceId,
                request.masterId,
                request.timeSlotId
        );

        return toResponse(booking);
    }

    // ================= UC-005 =================

    @GetMapping("/client/{clientId}")
    public List<BookingResponse> getByClient(@PathVariable Long clientId) {

        return bookingService.getBookingsByClient(clientId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ================= UC-005 =================

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }

    // ================= UC-009 =================

    @PostMapping("/{id}/confirm")
    public void confirm(@PathVariable Long id) {
        bookingService.confirmBooking(id);
    }

    private BookingResponse toResponse(Booking b) {
        BookingResponse r = new BookingResponse();
        r.id = b.getId();
        r.status = b.getStatus();
        r.dateTime = b.getDateTime();
        return r;
    }
}