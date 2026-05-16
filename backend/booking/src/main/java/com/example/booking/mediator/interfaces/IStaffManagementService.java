package com.example.booking.mediator.interfaces;

import com.example.booking.entity.Master;

public interface IStaffManagementService {

    Master createMaster(Master master);

    Master updateMaster(Long id, Master master);

    void updateAvailability(Long masterId, Long slotId, boolean available);

    void confirmServiceCompletion(Long bookingId);
}