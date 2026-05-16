package com.example.booking.control.controller;

import com.example.booking.entity.Master;
import com.example.booking.mediator.interfaces.IStaffManagementService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final IStaffManagementService service;

    public StaffController(IStaffManagementService service) {
        this.service = service;
    }

    @PutMapping("/{id}")
    public Master update(@PathVariable Long id,
                         @RequestBody Master master) {
        return service.updateMaster(id, master);
    }

    @PostMapping("/complete/{bookingId}")
    public void complete(@PathVariable Long bookingId) {
        service.confirmServiceCompletion(bookingId);
    }
}