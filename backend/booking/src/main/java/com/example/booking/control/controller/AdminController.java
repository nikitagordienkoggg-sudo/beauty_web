package com.example.booking.control.controller;

import com.example.booking.mediator.interfaces.IAdminService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final IAdminService service;

    public AdminController(IAdminService service) {
        this.service = service;
    }

    @PostMapping("/resolve/{bookingId}")
    public void resolve(@PathVariable Long bookingId) {
        service.resolveDispute(bookingId);
    }
}